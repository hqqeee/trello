package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.card.ChangeCardUserRequest;
import com.ruslan.backendtrello.payload.request.card.CreateCardRequest;
import com.ruslan.backendtrello.payload.request.card.EditCardRequest;
import com.ruslan.backendtrello.payload.request.card.GroupEditRequest;
import com.ruslan.backendtrello.payload.response.CreatedResponse;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.CardService;
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
public class CardController {
    private final BoardService boardService;
    private final UserService userService;
    private final CardService cardService;

    @PostMapping("/{boardId}/card")
    ResponseEntity<CreatedResponse> createCard(@PathVariable("boardId") Long boardId,
                                               @RequestBody CreateCardRequest createCardRequest,
                                               Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (user.isPresent() && board.isPresent()) {
            return ResponseEntity.ok(cardService.addCard(createCardRequest, board.get(), user.get().getId()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{boardId}/card")
    ResponseEntity<MessageResponse> editGroupOfCards(@PathVariable("boardId") Long boardId,
                                                     @RequestBody java.util.List<GroupEditRequest> cardsEdits,
                                                     Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (user.isPresent() && board.isPresent()) {
            return ResponseEntity.ok(cardService.editCards(cardsEdits, board.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{boardId}/card/{cardId}")
    ResponseEntity<MessageResponse> editCard(@PathVariable("boardId") Long boardId,
                                             @PathVariable("cardId") Long cardId,
                                             @RequestBody EditCardRequest editCardRequest,
                                             Authentication authentication) {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (user.isPresent() && board.isPresent()) {
            return ResponseEntity.ok(cardService.editCard(editCardRequest, board.get(), cardId));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{boardId}/card/{cardId}")
    ResponseEntity<MessageResponse> editCard(@PathVariable("boardId") Long boardId,
                                             @PathVariable("cardId") Long cardId,
                                             Authentication authentication){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (user.isPresent() && board.isPresent()) {
            return ResponseEntity.ok(cardService.deleteCard(board.get(), cardId));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{boardId}/card/{cardId}/users")
    ResponseEntity<MessageResponse> editsCardUsers(@PathVariable("boardId") Long boardId,
                                                   @PathVariable("cardId") Long cardId,
                                                   Authentication authentication,
                                                   @RequestBody ChangeCardUserRequest changeCardUserRequest){
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        Optional<Board> board = boardService.getBoardById(boardId, user);
        if (user.isPresent() && board.isPresent()) {
            return ResponseEntity.ok(cardService.editCardUsers(board.get(), cardId, changeCardUserRequest));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
