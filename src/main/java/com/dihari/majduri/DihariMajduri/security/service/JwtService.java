package com.dihari.majduri.DihariMajduri.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    private String secretKey;
    public JwtService(){
        secretKey=generateSecretKey();
    }
    private String generateSecretKey(){
        System.out.println("*******generate secret key *********");
        try
        {
            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
            SecretKey sKey=keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(sKey.getEncoded());

        }catch(Exception e){
            throw new RuntimeException("Error generating secret key",e);
        }
    }

    public String generateToken(String username){
        System.out.println("********Generate token method got called*******");
        Map<String,Object> claims=new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getKey(){
        System.out.println("*********getkey*********");
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token){
        System.out.println("*********extract username**********");
        System.out.println("********Token :"+token);
        return extractClaim(token, Claims::getSubject);
    }

    public<T> T extractClaim(String token, Function<Claims,T> claimResolver){
        System.out.println("*********extract claim*********");
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        System.out.println("**********extract all claims********");
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        System.out.println("************validate token*********");
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        System.out.println("**********is Token expired********");
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        System.out.println("**********extract expiration*******");
        return extractClaim(token,Claims::getExpiration);
    }
}
