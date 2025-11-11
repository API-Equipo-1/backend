package com.api.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import com.api.e_commerce.repository.UsuarioRepository;
import com.api.e_commerce.security.JwtFilter;

import lombok.RequiredArgsConstructor;

// Indica que esta clase contiene configuraciones de Spring
@Configuration
// Habilita la seguridad web de Spring Security
@EnableWebSecurity
// Genera un constructor con los campos final requeridos lombok
@RequiredArgsConstructor
public class SecurityConfig {

    // Inyección del repositorio de usuarios
    private final UsuarioRepository usuarioRepository;
    private final JwtFilter jwtFilter;

    // Cargar los datos del usuario desde tu sistema a través de UsuarioRepository
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                //TODO: ssanchez - capturar con globalexceptionhanlder @ControllerAdivce
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Recibe las credenciales del usuario (a través del UsernamePasswordAuthenticationToken)
    // Usa el UserDetailsService para buscar el usuario en la base de datos
    // Usa el PasswordEncoder para verificar si la contraseña proporcionada coincide con la almacenada
    // Si todo es correcto, crea un token de autenticación; si no, lanza una excepción
    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     authProvider.setUserDetailsService(userDetailsService());
    //     return authProvider;
    // }

    /**
     * AuthenticationManager es el componente central de autenticación en Spring Security.
     *
     * Funcionamiento:
     * 1. Recibe un objeto Authentication (UsernamePasswordAuthenticationToken en nuestro caso)
     * 2. Delega la autenticación a una cadena de AuthenticationProvider configurados
     * 3. Por defecto, usa DaoAuthenticationProvider que:
     *    - Utiliza UserDetailsService para cargar el usuario de la base de datos
     *    - Emplea PasswordEncoder para verificar la contraseña
     *    - Compara las credenciales proporcionadas con las almacenadas
     *
     * Proceso de autenticación:
     * - Entrada: Credenciales sin verificar (username/password)
     * - Proceso: Validación de credenciales
     * - Salida: Authentication completamente autenticado con authorities
     *
     * Si la autenticación falla, lanza AuthenticationException
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Define el codificador de contraseñas que se usará para encriptar y verificar passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura CORS (Cross-Origin Resource Sharing) para permitir peticiones desde otros dominios
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite solicitudes desde estos orígenes (frontend)
        // En producción, reemplazar "*" con la URL específica del frontend
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200", "http://localhost:5173", "http://localhost:80", "http://localhost"));
        
        // Permite todos los métodos HTTP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Permite todos los headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permite el envío de credenciales (cookies, headers de autorización)
        configuration.setAllowCredentials(true);
        
        // Expone estos headers en la respuesta
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        // Tiempo de caché de la configuración CORS (en segundos)
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Configura las reglas de seguridad para las diferentes rutas de la API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        //         .csrf(csrf -> csrf.disable())
        //         .authorizeHttpRequests(auth -> auth
        //                 // .requestMatchers("/api/productos/**").permitAll()
        //                 .requestMatchers("/api/auth/**").permitAll()
        //                 .anyRequest().authenticated());

        // return http.build();

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas que no requieren autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                        // Rutas que requieren autenticación para modificar productos
                        .requestMatchers(HttpMethod.POST, "/api/productos").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").authenticated()

                        // Rutas exclusivas para administradores
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Rutas de pedidos solo para usuarios autenticados
                        .requestMatchers("/api/pedidos/**").authenticated()

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\":401,\"error\":\"No autorizado\",\"message\":\"Se requiere autenticación para acceder a este recurso\",\"timestamp\":" + System.currentTimeMillis() + "}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\":403,\"error\":\"Acceso denegado\",\"message\":\"Se requiere un token JWT válido para acceder a este recurso\",\"timestamp\":" + System.currentTimeMillis() + "}");
                        }))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
