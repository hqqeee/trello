package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.sql.RefreshToken;
import com.ruslan.backendtrello.models.sql.Role;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.SignInRequest;
import com.ruslan.backendtrello.payload.request.SignUpRequest;
import com.ruslan.backendtrello.payload.request.TokenRefreshRequest;
import com.ruslan.backendtrello.payload.response.SignInResponse;
import com.ruslan.backendtrello.payload.response.SignUpResponse;
import com.ruslan.backendtrello.payload.response.TokenRefreshResponse;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.security.jwt.JwtUtils;
import com.ruslan.backendtrello.service.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public SignInResponse authenticate(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new SignInResponse(
            "Authorized",
                jwtToken,
                refreshToken.getToken()
        );
    }

    public SignUpResponse registerUser(SignUpRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            return new SignUpResponse(
                    "User with email " + request.getEmail() + " is already exists.");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN).build();
        userRepository.save(user);
        return new SignUpResponse(
                "Created",
                    user.getId()
        );
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) throws TokenRefreshException {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getEmail());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Token not in DB."));
    }

}
