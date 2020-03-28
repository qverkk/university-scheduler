package com.kul.database.filter;

import com.kul.database.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || token.isEmpty() || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return null;
        }

        try {
            byte[] signinKey = SecurityConstants.JWT_SECRET.getBytes();
            Jws<Claims> parsedToken = Jwts.parserBuilder()
                    .setSigningKey(signinKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""));
            String username = parsedToken.getBody().getSubject();
            List<String> list = (List<String>) parsedToken.getBody().get("rol");
            List<SimpleGrantedAuthority> result = list.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
//            List<SimpleGrantedAuthority> authorities = Arrays.asList(parsedToken.getBody().get("rol").toString().split(",")).stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
            if (!username.isEmpty()) {
                return new UsernamePasswordAuthenticationToken(username, null, result);
            }

        } catch (Exception e) {
            System.err.println("Error authentication");
        }
        return null;
    }
}
