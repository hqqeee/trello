package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.list.CreateListRequest;
import com.ruslan.backendtrello.payload.request.list.EditListsRequest;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.ListService;
import com.ruslan.backendtrello.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/{boardId}/list")
    ResponseEntity<MessageResponse> addList(Authentication authentication,
                                            @RequestBody CreateListRequest createListRequest,
                                            @PathVariable("boardId") Long boardId) {
       Optional<User> user = userService.getUserFromAuthentication(authentication);
       Optional<Board> board = boardService.getBoardById(boardId, user);
        return board.map(value -> ResponseEntity.ok(listService.addList(createListRequest, value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PutMapping("/{boardId}/list")
    ResponseEntity<MessageResponse> editList(Authentication authentication,
                                             @RequestBody java.util.List<EditListsRequest> editListsRequest,
                                             @PathVariable("boardId") Long boardId){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        System.out.println(editListsRequest);
        return board.map(value -> ResponseEntity.ok(listService.editLists(editListsRequest, value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}