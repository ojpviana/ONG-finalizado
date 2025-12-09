package com.protecao.animais.service.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec; 
import javax.xml.bind.DatatypeConverter; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.protecao.animais.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Service
public class JwtService {


    private final String CHAVE_SECRETA = "5F1G2H3J4K5L6M7N8P9Q0R1S2T3U4V5W6X7Y8Z9A0B1C2D3E4F5G6H7I8J9K0L1M";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() 
                .setSigningKey(getSignInKey())
                .build() 
                .parseClaimsJws(token)
                .getBody();
    }
    public String gerarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", usuario.getRole());
        return criarToken(claims, usuario.getUsername());
    }

    private String criarToken(Map<String, Object> claims, String userName) {
        long tempoExpiracaoMillis = 1000 * 60 * 60 * 24; 

        return Jwts.builder() 
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracaoMillis)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                
                .compact();
    }

    private Key getSignInKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(CHAVE_SECRETA);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }


    private Key getSigningKey() {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(CHAVE_SECRETA);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}