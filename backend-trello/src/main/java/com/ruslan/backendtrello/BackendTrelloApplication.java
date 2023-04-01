package com.ruslan.backendtrello;

import com.ruslan.backendtrello.repository.BoardRepository;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendTrelloApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendTrelloApplication.class, args);
	}

	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final AuthService authService;

}
