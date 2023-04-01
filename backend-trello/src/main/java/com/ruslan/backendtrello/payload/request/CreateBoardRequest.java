package com.ruslan.backendtrello.payload.request;

import jakarta.validation.constraints.NotBlank;

public record CreateBoardRequest(@NotBlank String title, Object custom) {
}
