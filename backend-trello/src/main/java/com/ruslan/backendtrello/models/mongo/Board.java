package com.ruslan.backendtrello.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ruslan.backendtrello.models.mongo.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    private Long id;
    private String title;
    private Object custom;
    @DBRef
    private java.util.List<List> lists;
}
