package com.ruslan.backendtrello.payload.response;

import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.models.sql.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDetailed {
    private String title;
    private Object custom;
    private java.util.List<User> users;
    private java.util.List<List> lists;
}
