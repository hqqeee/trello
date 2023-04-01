package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.service.BoardService;
import com.ruslan.backendtrello.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/{id}")
    ResponseEntity<Board> getById(@RequestParam("id") Long id){
        return boardSer
    }
}
