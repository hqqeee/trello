package com.ruslan.backendtrello.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    private java.util.List<Long> ownersId;
    @DBRef
    private java.util.List<List> lists;
    @Transient
    public static final String SEQUENCE_NAME = "boards_sequence";
}
