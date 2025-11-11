package com.api.e_commerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import com.api.e_commerce.exception.CustomAuthenticationException;
import com.api.e_commerce.exception.CustomAccessDeniedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Este método se ejecuta en cada petición HTTP para verificar si existe un token JWT válido.
     * - Se configura en `SecurityConfig` para que se ejecute antes que el filtro de autenticación de Spring Security.
     * - Intercepta todas las peticiones entrantes a la API.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Verificar si es una ruta pública
        if (isPublicPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomAuthenticationException("No se proporcionó el token JWT");
        }

        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomAccessDeniedException("Token JWT inválido o expirado");
        }

        String username = jwtUtil.getUsername(token);
        Set<String> roles = jwtUtil.getRoles(token);

        // transformar el conjunto de roles en la lista de autoridades
        var authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // Rutas públicas:
        // - Todas las rutas de autenticación
        // - GET a productos (consulta pública)
        return path.startsWith("/api/auth/") || 
               (path.startsWith("/api/productos") && method.equals("GET"));
    }
}
