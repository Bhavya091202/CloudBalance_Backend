package com.example.CloudBalanceBackend.security.jwt;

import com.example.CloudBalanceBackend.repository.BlackListTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final BlackListTokenRepository blackListTokenRepository;
    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService, BlackListTokenRepository blackListTokenRepository) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.blackListTokenRepository = blackListTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            if(blackListTokenRepository.existsByToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response
                    .getWriter()
                    .write(
                        """
                            {
                                  "status": 401,
                                  "error": "Unauthorized",
                                  "message": "Token is blacklisted.",
                                  "path": "%s"
                            }
                        """
                        .formatted(request.getRequestURI())
                    );
                return;
            }
            //valid token
            String email = jwtUtils.extractUsername(token);

            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // new added for token gen at every req
                    String newAccessToken = jwtUtils.generateAccessToken(userDetails);
                    response.setHeader("X-New-Access-Token", newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

//@Component
//public class AuthTokenFilter extends OncePerRequestFilter {
//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//    private final BlackListTokenRepository blackListTokenRepository;
//
//    @Autowired
//    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService, BlackListTokenRepository blackListTokenRepository) {
//        this.jwtUtils = jwtUtils;
//        this.userDetailsService = userDetailsService;
//        this.blackListTokenRepository = blackListTokenRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        try {
//            String bearerToken = request.getHeader("Authorization");
//
//            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//                String token = bearerToken.substring(7);
//
//                if (blackListTokenRepository.existsByToken(token)) {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.setContentType("application/json");
//                    response.getWriter().write("""
//                        {
//                          "status": 401,
//                          "error": "Unauthorized",
//                          "message": "Token is blacklisted.",
//                          "path": "%s"
//                        }
//                        """.formatted(request.getRequestURI()));
//                    return;
//                }
//
//                String email = jwtUtils.extractUsername(token);
//
//                if (email != null) {
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//                    if (jwtUtils.validateToken(token)) {
//                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                        authToken
//                                .setDetails(
//                                        new WebAuthenticationDetailsSource()
//                                                .buildDetails(request));
//
//                        SecurityContextHolder
//                                .getContext()
//                                .setAuthentication(authToken);
//
//                        String newAccessToken = jwtUtils.generateAccessToken(userDetails);
//                        response.setHeader("X-New-Access-Token", newAccessToken);
//                    }
//                }
//            }
//
//            filterChain.doFilter(request, response);
//
//        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("""
//                {
//                  "status": 401,
//                  "error": "Unauthorized",
//                  "message": "Token has expired. Please login again.",
//                  "path": "%s"
//                }
//                """.formatted(request.getRequestURI()));
//        } catch (Exception ex) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("""
//                {
//                  "status": 401,
//                  "error": "Unauthorized",
//                  "message": "Invalid or malformed token.",
//                  "path": "%s"
//                }
//                """.formatted(request.getRequestURI()));
//        }
//    }
//}
