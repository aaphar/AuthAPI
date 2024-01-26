package com.aphar.registration.config;

import com.aphar.registration.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        // check if we have jwt token,
        // it will be in header called Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7).trim();

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        // if all the token is valid
        if (storedToken != null) {
            // implement logout, revoke everything
            storedToken.setExpired(true);
            storedToken.setRevoked(true);

            tokenRepository.save(storedToken);
        }
    }
}
