package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.models.sql.ShortUserInfo;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.board.CreateBoardRequest;
import com.ruslan.backendtrello.payload.request.board.PutBoardRequest;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.payload.response.CreatedResponse;
import com.ruslan.backendtrello.payload.response.UserResponse;
import com.ruslan.backendtrello.payload.response.board.BoardDetailedResponse;
import com.ruslan.backendtrello.payload.response.board.BoardShortResponse;
import com.ruslan.backendtrello.payload.response.board.BoardsResponse;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
                                                   Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        if (user.isPresent()) {
            BoardDetailedResponse boardDetailed = boardService.getById(id);
            if (boardService.getOwnersBuId(id).stream()
                    .anyMatch(owner -> Objects.equals(owner.getId(), user.get().getId()))) {
                return ResponseEntity.ok(boardService.getById(id));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    ResponseEntity<BoardsResponse> getAllUsersBoards(Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        return user
                .map(value -> ResponseEntity.ok(new BoardsResponse(boardService.getAllUsersBoards(value))))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteBoard(@PathVariable("id") Long id, Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        return ResponseEntity.ok(boardService.deleteBoard(id, user));
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateBoard(@PathVariable("id") Long id,
                                                @RequestBody PutBoardRequest putBoardRequest,
                                                Authentication authentication) {
        return ResponseEntity.ok(boardService.putBoard(id, putBoardRequest
                , userService.getUserFromAuthentication(authentication)));
    }

    @PostMapping
    ResponseEntity<CreatedResponse> createBoard(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        if (user.isPresent()) {
            CreatedResponse boardCreatedResponse =
                    boardService.addBoard(createBoardRequest, user.get().getId());
            return boardCreatedResponse.getId() == null ?
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(boardCreatedResponse) :
                    ResponseEntity.status(HttpStatus.CREATED).body(boardCreatedResponse);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{boardId}/user/{userId}")
    ResponseEntity<UserResponse> getUserById(
            @PathVariable("boardId") Long boardId,
            @PathVariable("userId") Long userId,
            Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (board.isPresent()) {
            if (board.get().getLists().stream().map(List::getCards)
                    .flatMap(Collection::stream)
                    .anyMatch(card -> card.getUserIds().contains(userId))) {
                return ResponseEntity.ok(new UserResponse(userId, userService.getUserById(userId).getEmail()));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
