package com.ruslan.backendtrello.payload.response.board;

import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.models.sql.ShortUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDetailedResponse {
    private String title;
    private Object custom;
    private java.util.List<ShortUserInfo> users;
    private java.util.List<List> lists;
}
