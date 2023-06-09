package com.ruslan.backendtrello.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class List {
    @Id
    private Long id;
    private Integer position;
    private String title;
    private java.util.List<Card> cards;
    @Transient
    public static final String SEQUENCE_NAME = "lists_sequence";
}

