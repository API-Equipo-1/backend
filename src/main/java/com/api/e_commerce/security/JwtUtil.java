package com.api.e_commerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

//Instancia Única: Spring creará una sola instancia de la clase JwtUtil cuando se inicie la aplicación. 
//Inyección de Dependencias: Permite que esta instancia sea inyectada automáticamente en otras clases que la necesiten
//sin la necesidad  de  crearla manualmente.
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    //genera clave secreta para firmar el token a partir de secret definido en application.properties
    private SecretKey getSigningKey() {
        // La clave debe tener al menos 256 bits para el algoritmo HS256
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //  Genera un token JWT para un usuario.
    //  El token incluye el nombre de usuario (subject), sus roles, la fecha de emisión y una fecha de expiración.
    //  Finalmente, se firma el token con la clave secreta.
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", String.join(",", roles))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //extraee el nombre del usuario del token
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    //extrae los roles del usario del token
    public Set<String> getRoles(String token) {
        String roles = (String) getClaims(token).get("roles");
        return Set.of(roles.split(","));
    }


    //  Valida un token JWT.
    //  Comprueba que el token no haya expirado y que la firma sea correcta.
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //Valida el token y también extrae los "claims" (datos) del token JWT.
    //Utiliza la clave de firma para verificar la integridad del token antes de extraer los datos.
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}