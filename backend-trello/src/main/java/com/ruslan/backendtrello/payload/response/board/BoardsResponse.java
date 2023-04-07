package com.ruslan.backendtrello.payload.response.board;

import com.ruslan.backendtrello.models.mongo.Board;

import java.util.List;

public record BoardsResponse(List<BoardShortResponse> boards) {
}
