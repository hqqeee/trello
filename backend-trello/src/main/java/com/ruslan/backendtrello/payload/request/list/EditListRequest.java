package com.ruslan.backendtrello.payload.request.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditListRequest {
    private String title;
    private Integer position;
}
