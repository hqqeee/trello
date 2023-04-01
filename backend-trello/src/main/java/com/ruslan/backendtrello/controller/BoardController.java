package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.CreateBoardRequest;
import com.ruslan.backendtrello.payload.response.BoardCreatedResponse;
import com.ruslan.backendtrello.payload.response.BoardDetailed;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/{id}")
    ResponseEntity<BoardDetailed> getById(
            @PathVariable("id") Long id,
            Authentication authentication){
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        BoardDetailed boardDetailed = boardService.getById(id);
        if(boardDetailed.getUsers().contains(user)){
            return ResponseEntity.ok(boardService.getById(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    ResponseEntity<BoardCreatedResponse> createBoard(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication){
        String email = authentication.getName();
        BoardCreatedResponse boardCreatedResponse =
                boardService.addBoard(createBoardRequest, userService.getUserByEmail(email).getId());
        return boardCreatedResponse.getId() == null?
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(boardCreatedResponse) :
                ResponseEntity.status(HttpStatus.CREATED).body(boardCreatedResponse);
    }
}
