package com.ruslan.backendtrello.payload.request.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {
    private String title;
    private Long listId;
    private Integer position;
    private String description;
    private Object custom;
}
