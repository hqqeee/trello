package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.payload.request.auth.SignInRequest;
import com.ruslan.backendtrello.payload.request.auth.SignUpRequest;
import com.ruslan.backendtrello.payload.request.auth.TokenRefreshRequest;
import com.ruslan.backendtrello.payload.response.auth.SignInResponse;
import com.ruslan.backendtrello.payload.response.auth.SignUpResponse;
import com.ruslan.backendtrello.payload.response.auth.TokenRefreshResponse;
import com.ruslan.backendtrello.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> authenticateUser(@Valid @RequestBody SignInRequest loginRequest){
        SignInResponse signInResponse = authService.authenticate(loginRequest);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse = authService.registerUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken
            (@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest){
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(tokenRefreshRequest);
        return ResponseEntity.ok(tokenRefreshResponse);
    }
}
