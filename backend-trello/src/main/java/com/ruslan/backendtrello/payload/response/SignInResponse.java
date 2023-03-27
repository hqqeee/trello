package com.ruslan.backendtrello.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignInResponse {
    private final String result;
    private String token;
    private String refreshToken;
}
