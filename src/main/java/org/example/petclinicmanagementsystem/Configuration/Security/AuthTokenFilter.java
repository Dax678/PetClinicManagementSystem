package org.example.petclinicmanagementsystem.Configuration.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petclinicmanagementsystem.Service.JwtService;
import org.example.petclinicmanagementsystem.Service.UserInfoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserInfoDetailsService userInfoDetailsService;

    @Autowired
    public AuthTokenFilter(JwtService jwtService, UserInfoDetailsService userInfoDetailsService) {
        this.jwtService = jwtService;
        this.userInfoDetailsService = userInfoDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            String AuthorizationHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            if (AuthorizationHeader != null && AuthorizationHeader.startsWith("Bearer ")) {
                token = AuthorizationHeader.substring(7);
                username = jwtService.extractUsername(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userInfoDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e);
        }
        filterChain.doFilter(request, response);
    }
}
