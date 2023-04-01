package com.ruslan.backendtrello.payload.response.auth;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignInResponse {
    private final String result;
    private String token;
    private String refreshToken;
}
