package com.aphar.registration.token;

import com.aphar.registration.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello() {
        System.out.println("hi");
        return ResponseEntity.ok("hi");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody String token) {
        final String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(token, userDetails)) {
            return ResponseEntity.ok("Token is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not valid.");
        }
    }
}

