package com.revature.security;

import com.revature.models.Employee;
import com.revature.daos.EmployeeDAO;
import com.revature.services.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.JwtDsl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.management.JMRuntimeException;
import java.util.Date;

@Component
public class JwtGenerator {
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Autowired
    private EmployeeService employeeService;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
        Employee employee = employeeService.findEmployeeByUsername(username);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("Id", employee.getId())
                .claim("Position", employee.getPosition().getName())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT Token is expired or invalid");
        }

    }
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
