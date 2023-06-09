package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.sql.ShortUserInfo;
import com.ruslan.backendtrello.models.sql.User;
import com.ruslan.backendtrello.payload.request.board.CreateBoardRequest;
import com.ruslan.backendtrello.payload.request.board.PutBoardRequest;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.payload.response.CreatedResponse;
import com.ruslan.backendtrello.payload.response.board.BoardDetailedResponse;
import com.ruslan.backendtrello.payload.response.board.BoardShortResponse;
import com.ruslan.backendtrello.repository.BoardRepository;
import com.ruslan.backendtrello.repository.UserRepository;
import com.ruslan.backendtrello.service.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDetailedResponse getById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found."));
        return convertBoardDetailed(board);
    }

    public List<User> getOwnersBuId(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found."));
        return userRepository.findAllById(board.getOwnersId());
    }

    public Optional<Board> getBoardById(Long id, Optional<User> user){
        if(user.isPresent()){
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found."));
            if(board.getOwnersId().contains(user.get().getId())){
                return Optional.of(board);
            }
        }
        return Optional.empty();
    }

    public CreatedResponse addBoard(CreateBoardRequest createBoardRequest, Long userId) {
        try {
            Board board = boardRepository.save(
                    Board.builder()
                            .title(createBoardRequest.title())
                            .custom(createBoardRequest.custom())
                            .lists(Collections.emptyList())
                            .ownersId(Collections.singletonList(userId))
                            .build());
            return new CreatedResponse("Created", board.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return new CreatedResponse("Cannot create board. Error: " + e.getMessage());
        }
    }

    public java.util.List<BoardShortResponse> getAllUsersBoards(User user) {
        return boardRepository.findByOwnersIdContains(user.getId())
                .stream()
                .map(this::convertToShortBoardInfo)
                .toList();
    }

    public MessageResponse deleteBoard(Long boardId, Optional<User> user) {
        if (user.isPresent() &&
                boardRepository.findById(boardId)
                        .stream()
                        .anyMatch(board -> Objects.equals(board.getId(), boardId))) {
            boardRepository.deleteById(boardId);
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Cannot delete.");
    }

    public MessageResponse putBoard(Long boardId, PutBoardRequest putBoardRequest, Optional<User> user) {
        if (user.isPresent() &&
                boardRepository.findById(boardId)
                        .stream()
                        .anyMatch(board -> Objects.equals(board.getId(), boardId))) {
            Optional<Board> optionalBoard = boardRepository.findById(boardId);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                System.out.println(board);
                System.out.println(putBoardRequest);
                if (putBoardRequest.getTitle() != null && !putBoardRequest.getTitle().isEmpty()) {
                    board.setTitle(putBoardRequest.getTitle());
                }
                if (putBoardRequest.getCustom() != null) {
                    board.setCustom(putBoardRequest.getCustom());
                }
                if (putBoardRequest.getLists() != null) {
                    board.setLists(putBoardRequest.getLists());
                }
                boardRepository.save(board);
                return new MessageResponse("Updated");
            }
        }
        return new MessageResponse("Cannot update board.");
    }

    private BoardDetailedResponse convertBoardDetailed(Board board) {
        return new BoardDetailedResponse(
                board.getTitle(),
                board.getCustom(),
                board.getLists()
        );
    }

    private BoardShortResponse convertToShortBoardInfo(Board board) {
        return new BoardShortResponse(
                board.getId(),
                board.getTitle(),
                board.getCustom()
        );
    }
}
