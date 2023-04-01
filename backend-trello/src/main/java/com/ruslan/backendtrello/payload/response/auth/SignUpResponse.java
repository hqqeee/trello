package com.ruslan.backendtrello.payload.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignUpResponse {
    private final String result;
    private Long id;
}
