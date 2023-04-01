package com.ruslan.backendtrello.models.mongo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    private Long id;
    private String title;
    private String description;
    private String color; // TODO enum
    private Object custom;
    private List<Integer> userIds;
    private LocalDateTime createdAt;
}