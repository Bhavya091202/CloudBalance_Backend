package com.example.CloudBalanceBackend.service.Implimentation;

import com.example.CloudBalanceBackend.dto.LoginDTO;
import com.example.CloudBalanceBackend.model.BlackListToken;
import com.example.CloudBalanceBackend.model.User;
import com.example.CloudBalanceBackend.repository.BlackListTokenRepository;
import com.example.CloudBalanceBackend.repository.UserRepository;
import com.example.CloudBalanceBackend.security.dto.UserDetailsImpl;
import com.example.CloudBalanceBackend.security.jwt.JwtUtils;
import com.example.CloudBalanceBackend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final BlackListTokenRepository blackListTokenRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                           BlackListTokenRepository blackListTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.blackListTokenRepository = blackListTokenRepository;
    }

    @Override
    public ResponseEntity<?> authenticate(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateAccessToken(userDetails);

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "id", userDetails.getId(),
                "role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("ROLE_CUSTOMER"),
                "firstName", userDetails.getFirstName(),
                "lastName", userDetails.getLastName(),
                "tokenType", "Bearer"
        ));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Date expiry = jwtUtils.extractExpiration(token);

            BlackListToken blackListToken = new BlackListToken();
            blackListToken.setToken(token);
            blackListToken.setExpiryDate(expiry);

            blackListTokenRepository.save(blackListToken);

            return ResponseEntity.ok(Map.of("message", "Logged out successfully and token blacklisted"));
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Invalid Token Access"));
    }
}
