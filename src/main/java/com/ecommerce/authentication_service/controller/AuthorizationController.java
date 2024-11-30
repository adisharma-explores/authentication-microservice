package com.ecommerce.authentication_service.controller;


import com.ecommerce.authentication_service.config.JwtUtil;
import com.ecommerce.authentication_service.model.Customer;
import com.ecommerce.authentication_service.request.AuthenticationRequest;
import com.ecommerce.authentication_service.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthorizationController {
    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final Customer customer = customerService.getUserByUsername(request.getEmail());
        String presentedPassword = request.getPassword();
        if (!passwordEncoder.matches(presentedPassword, customer.getCustomerPassword())) {
            throw new RuntimeException("Bad credentials");
        }
        if (customer != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", customer.getRole().stream().map(Objects::toString).collect(Collectors.toList()));
            claims.put("userId",customer.getCustomerId());
            return ResponseEntity.ok(jwtUtil.generateToken(customer.getCustomerUserName(),claims));
        }
        return ResponseEntity.status(400).body("error");
    }

}
