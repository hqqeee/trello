package com.ruslan.backendtrello.payload.request.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GroupEditRequest(Long id, Integer position, @JsonProperty("list_id") Long listId) {
}
