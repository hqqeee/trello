package com.ruslan.backendtrello.payload.request.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeCardUserRequest {
    private java.util.List<Long> add;
    private java.util.List<Long> remove;
}
