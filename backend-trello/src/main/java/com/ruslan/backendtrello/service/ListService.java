package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.payload.request.list.CreateListRequest;
import com.ruslan.backendtrello.payload.request.list.EditListRequest;
import com.ruslan.backendtrello.payload.request.list.EditListsRequest;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListService {
    final BoardRepository boardRepository;
    public MessageResponse addList(CreateListRequest createListRequest, Board board){
        if (board.getLists() == null
                || (board.getLists().size() > 0
                && board.getLists().get(0) == null)) {
            board.setLists(new ArrayList<>());
        }
        List list = new List(
                (long) board.getLists().size(),
                createListRequest.position(),
                createListRequest.title(),
                Collections.emptyList());
        board.getLists().add(list);
        System.out.println(createListRequest);
        boardRepository.save(board);
        return new MessageResponse("Created");
    }

    public MessageResponse editLists(java.util.List<EditListsRequest> editListsRequests, Board board) {
        java.util.List<List> boardLists = board.getLists();
        editListsRequests.forEach(list -> boardLists.stream().filter(l -> Objects.equals(l.getId(), list.id()))
                    .forEach(l -> l.setPosition(list.position()))
        );
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }

    public MessageResponse editList(EditListRequest editListRequest, Board board, Long listId){
        Optional<List> list = board.getLists().stream().filter(l -> Objects.equals(l.getId(), listId)).findFirst();
        if(list.isPresent()){
            if(editListRequest.getTitle() != null){
                list.get().setTitle(editListRequest.getTitle());
            }
            if(editListRequest.getPosition() != null){
                list.get().setPosition(editListRequest.getPosition());
            }
        }
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }

    public MessageResponse deleteList(Board board, Long listId) {
        java.util.List<List> boardLists = board.getLists();
        boardLists.removeIf(list -> Objects.equals(list.getId(), listId));
        boardRepository.save(board);
        return new MessageResponse("Deleted");
    }
}
