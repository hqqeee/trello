package com.ruslan.backendtrello.payload.request.board;

import com.ruslan.backendtrello.models.mongo.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutBoardRequest {
    private Long id;
    private String title;
    private Object custom;
    private java.util.List<List> lists;
}
