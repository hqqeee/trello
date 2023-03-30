package com.ruslan.backendtrello.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private String color; // TODO enum
    private Object custom;
    @OneToOne
    private List<User> userList;
    private LocalDateTime createdAt;
}
