package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.sql.ShortUserInfo;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.board.CreateBoardRequest;
import com.ruslan.backendtrello.payload.request.board.PutBoardRequest;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.payload.response.board.BoardCreatedResponse;
import com.ruslan.backendtrello.payload.response.board.BoardDetailedResponse;
import com.ruslan.backendtrello.payload.response.board.BoardShortResponse;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/{id}")
    ResponseEntity<BoardDetailedResponse> getById( // TODO refactor
            @PathVariable("id") Long id,
            Authentication authentication){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        if(user.isPresent()){
            BoardDetailedResponse boardDetailed = boardService.getById(id);
            if(boardDetailed.getUsers()
                    .stream().map(ShortUserInfo::id)
                    .anyMatch(userId -> Objects.equals(userId, user.get().getId()))){
                return ResponseEntity.ok(boardService.getById(id));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    ResponseEntity<java.util.List<BoardShortResponse>> getAllUsersBoards(Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        return user
                .map(value -> ResponseEntity.ok(boardService.getAllUsersBoards(value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteBoard(@PathVariable("id") Long id, Authentication authentication){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        return ResponseEntity.ok(boardService.deleteBoard(id, user));
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateBoard(@PathVariable("id") Long id,
                                                @RequestBody  PutBoardRequest putBoardRequest,
                                                Authentication authentication){
        return  ResponseEntity.ok(boardService.putBoard(id, putBoardRequest
                , userService.getUserFromAuthentication(authentication)));
    }

    @PostMapping
    ResponseEntity<BoardCreatedResponse> createBoard(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        if(user.isPresent()){
            BoardCreatedResponse boardCreatedResponse =
                    boardService.addBoard(createBoardRequest, user.get().getId());
            return boardCreatedResponse.getId() == null?
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(boardCreatedResponse) :
                    ResponseEntity.status(HttpStatus.CREATED).body(boardCreatedResponse);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
