package com.mycompany.nubulamusicwebaplication.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JWTUtil {

    // 🔐 Llave secreta (para clase)
    private static final String SECRET = "mi_super_secreto_12345678912345678910";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔐 Generar token JWT
    public static String generarToken(String usuario) {
        return Jwts.builder()
                // ⭐ "subject" → quién es el usuario (puede ser correo, id, username, etc.)
                .subject(usuario)
                // ⏰ Fecha en la que se genera el token
                .issuedAt(new Date())
                // ⏳ Fecha de expiración (aquí: 1 hora)
                // System.currentTimeMillis() = tiempo actual en milisegundos
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                // 🔐 Firma del token con la llave secreta
                // Esto garantiza que el token no sea modificado
                .signWith(KEY)
                // 📦 Convierte todo en un String compacto (el JWT final)
                .compact();
    }

    // 🔍 Validar token JWT
    public static String validarToken(String token) {
        return Jwts.parser()
                // 🔐 Verifica la firma usando la misma llave secreta
                // Si el token fue alterado → aquí falla
                .verifyWith(KEY)
                // ⚙ Construye el parser
                .build()
                // 📦 Parsea el token y valida:
                // – firma
                // – expiración
                // – estructura
                .parseSignedClaims(token)
                // 📄 Obtiene el contenido del token (payload)
                .getPayload() // 🔓 Regresa el subject (usuario)
                .getSubject();
    }
}
