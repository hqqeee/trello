package com.ruslan.backendtrello.payload.response.board;

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
