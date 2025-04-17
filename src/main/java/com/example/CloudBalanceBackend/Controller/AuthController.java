//package com.example.CloudBalanceBackend.Controller;
//
//
//import com.example.CloudBalanceBackend.DTO.LoginDTO;
//import com.example.CloudBalanceBackend.Model.BlackListToken;
//import com.example.CloudBalanceBackend.Repository.BlackListTokenRepository;
//import com.example.CloudBalanceBackend.Security.dto.UserDetailsImpl;
//import com.example.CloudBalanceBackend.Security.jwt.JwtUtils;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//    private final BlackListTokenRepository blackListTokenRepository;
//
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailsService userDetailsService, BlackListTokenRepository blackListTokenRepository) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtils = jwtUtils;
//        this.userDetailsService = userDetailsService;
//        this.blackListTokenRepository = blackListTokenRepository;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDTO.getEmail(),
//                        loginDTO.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String accessToken = jwtUtils.generateAccessToken(userDetails); // generate jwt
//        return ResponseEntity.ok(Map.of(
//                "accessToken", accessToken,
//                "role", userDetails.getAuthorities()
//                        .stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .findFirst()
//                        .orElse("ROLE_CUSTOMER"),
//                "firstName", userDetails.getFirstName(),
//                "lastName", userDetails.getLastName(),
//                "tokenType", "Bearer"
//        ));
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
//    @PostMapping("logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            // Extract expiry from token
//            Date expiry = jwtUtils.extractExpiration(token);
//            BlackListToken blackListToken = new BlackListToken();
//            blackListToken.setToken(token);
//            blackListToken.setExpiryDate(expiry);
//            blackListTokenRepository.save(blackListToken);
//            return ResponseEntity.ok(Map.of("message", "Logged out successfully and token Blacklisted"));
//        }
//            return ResponseEntity.badRequest().body(Map.of("error", "Invalid Token Access"));
//    }
//}

package com.example.CloudBalanceBackend.Controller;

import com.example.CloudBalanceBackend.DTO.LoginDTO;
import com.example.CloudBalanceBackend.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.authenticate(loginDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}
