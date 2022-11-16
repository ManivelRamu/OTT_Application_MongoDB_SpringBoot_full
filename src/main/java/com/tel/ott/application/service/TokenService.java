package com.tel.ott.application.service;

import com.tel.ott.application.collection.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public static String secret = "user_auth_key";

    public String createToken(User user) {
    	 long millisec = System.currentTimeMillis();
         Date issuedate = new Date(millisec);

         Claims claims = Jwts.claims().setIssuedAt(issuedate).setIssuer(String.valueOf
                 (user.getEmail()));

         claims.put("name", user.getUsername());
         claims.put("role", user.getRole());
         claims.put("email", user.getEmail());

         String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact();
         return token;
    }

    public String getRoleFromToken(String token) {
    	Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }

    public boolean validateToken(String validate) throws Exception {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(validate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
