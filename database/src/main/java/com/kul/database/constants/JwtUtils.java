package com.kul.database.constants;

import com.kul.database.model.Authority;
import com.kul.database.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtUtils {

    public static String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Authority authority = user.getAuthority();

        byte[] signinKey = SecurityConstants.JWT_SECRET.getBytes();
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signinKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", authority)
                .compact();
        return token;
    }

    public static String getUsernameFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static Boolean validateToken(String token) {
        byte[] signinKey = SecurityConstants.JWT_SECRET.getBytes();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(signinKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
