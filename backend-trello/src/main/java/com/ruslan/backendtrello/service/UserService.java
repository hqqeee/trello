package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    public List<User> getUsersByIds(List<Long> ids){
        return userRepository.findAllById(ids);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }
}
