package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.Role;
import com.ruslan.backendtrello.models.User;
import com.ruslan.backendtrello.payload.request.SignInRequest;
import com.ruslan.backendtrello.payload.request.SignUpRequest;
import com.ruslan.backendtrello.payload.response.SignInResponse;
import com.ruslan.backendtrello.payload.response.SignUpResponse;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.security.jwt.JwtUtils;
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

    public SignInResponse authenticate(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new SignInResponse(
            "Authorized",
                jwtToken
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

}
