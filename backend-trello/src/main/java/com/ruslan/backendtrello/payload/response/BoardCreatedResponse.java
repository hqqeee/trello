package com.ruslan.backendtrello.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BoardCreatedResponse {
    private final String message;
    private Long id;
}
