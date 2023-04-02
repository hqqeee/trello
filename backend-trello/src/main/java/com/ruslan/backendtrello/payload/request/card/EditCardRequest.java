package com.ruslan.backendtrello.payload.request.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditCardRequest {
    private String title;
    private String description;
    @JsonProperty("list_id")
    private Long listId;
}
