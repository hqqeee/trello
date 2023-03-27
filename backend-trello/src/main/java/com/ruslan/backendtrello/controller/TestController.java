package com.ruslan.backendtrello.controller;

import com.ruslan.backendtrello.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @GetMapping("/hello")
    public ResponseEntity<MessageResponse> hello(){
        return ResponseEntity.ok(new MessageResponse(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
