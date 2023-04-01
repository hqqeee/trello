package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.CreateBoardRequest;
import com.ruslan.backendtrello.payload.response.BoardCreatedResponse;
import com.ruslan.backendtrello.payload.response.BoardDetailed;
import com.ruslan.backendtrello.repository.BoardRepository;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.service.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDetailed getById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found."));
        java.util.List<User> owners = userRepository.findAllById(board.getOwnersId());
        return convertBoardDetailed(board, owners);
    }

    public BoardCreatedResponse addBoard(CreateBoardRequest createBoardRequest, Long userId){
        try{
            Board board = boardRepository.save(
                    Board.builder()
                            .title(createBoardRequest.title())
                            .custom(createBoardRequest.custom())
                            .lists(Collections.emptyList())
                            .ownersId(Collections.singletonList(userId))
                            .build());
            return new BoardCreatedResponse("Created", board.getId());
        } catch (Exception e){
            e.printStackTrace();
            return new BoardCreatedResponse("Cannot create board. Error: " + e.getMessage());
        }
    }

    private BoardDetailed convertBoardDetailed(Board board, java.util.List<User> owners){
        return new BoardDetailed(
                board.getTitle(),
                board.getCustom(),
                owners,
                board.getLists()
        );
    }
}
