package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.Card;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.payload.request.card.ChangeCardUserRequest;
import com.ruslan.backendtrello.payload.request.card.CreateCardRequest;
import com.ruslan.backendtrello.payload.request.card.EditCardRequest;
import com.ruslan.backendtrello.payload.request.card.GroupEditRequest;
import com.ruslan.backendtrello.payload.response.CreatedResponse;
import com.ruslan.backendtrello.payload.response.MessageResponse;
import com.ruslan.backendtrello.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    @Value("${trello.card.defaultColor}")
    private String defaultColor;
    private final BoardRepository boardRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public CreatedResponse addCard(CreateCardRequest createCardRequest, Board board, Long userId) {
        Card card = new Card(
                sequenceGeneratorService.generateSequence(Card.SEQUENCE_NAME),
                createCardRequest.getTitle(),
                createCardRequest.getDescription(),
                createCardRequest.getPosition(),
                defaultColor,
                createCardRequest.getCustom(),
                Collections.singletonList(userId),
                LocalDateTime.now());
        Optional<List> list = board.getLists().stream()
                .filter(l -> Objects.equals(l.getId(), createCardRequest.getListId()))
                .findFirst();
        System.out.println(list);
        if (list.isPresent()) {
            list.get().getCards().add(card);
            boardRepository.save(board);
        }
        return new CreatedResponse("Created", card.getId());
    }

    public MessageResponse editCards(java.util.List<GroupEditRequest> cardsEdits, Board board) {
        Map<Long, List> listsMap = board.getLists().stream().collect(Collectors.toMap(List::getId, Function.identity()));
        cardsEdits.parallelStream().forEach(req -> {
            List newList = listsMap.get(req.listId());
            if (newList != null) {
                java.util.List<Card> removedCards = new ArrayList<>();
                board.getLists().forEach(
                        list -> list.getCards().removeIf(card -> {
                            if (card != null && card.getId().equals(req.id())) {
                                card.setPosition(req.position());
                                removedCards.add(card);
                                return true;
                            }
                            return false;
                        })
                );
                if (removedCards.size() > 0) {
                    newList.getCards().addAll(removedCards);
                }
            }
        });
        board.setLists(new ArrayList<>(listsMap.values()));
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }

    public MessageResponse editCard(EditCardRequest editCardRequest, Board board, Long cardId) {
        Optional<Card> cardToUpdate = board.getLists().stream()
                .map(List::getCards)
                .flatMap(Collection::stream)
                .filter(card -> card.getId().equals(cardId))
                .findFirst();
        if(cardToUpdate.isPresent()){
            if(editCardRequest.getDescription() != null){
                cardToUpdate.get().setDescription(editCardRequest.getDescription() );
            }
            if(editCardRequest.getTitle() != null){
                cardToUpdate.get().setTitle(editCardRequest.getTitle());
            }
            if(editCardRequest.getListId() != null) {
               deleteCard(board, cardId); // delete from old list
                board.getLists().stream().filter(list -> list.getId().equals(editCardRequest.getListId()))
                        .findFirst().ifPresent(l -> l.getCards().add(cardToUpdate.get())); // add to new
            }
        }
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }

    public MessageResponse deleteCard(Board board, Long cardId) {
        board.getLists()
                .forEach(list -> list.getCards()
                        .removeIf(card -> card.getId().equals(cardId)));
        boardRepository.save(board);
        return new MessageResponse("Deleted");
    }

    public MessageResponse editCardUsers(Board board, Long cardId, ChangeCardUserRequest changeCardUserRequest) {
        board.getLists().stream()
                .map(List::getCards)
                .flatMap(Collection::stream)
                .filter(card -> card.getId().equals(cardId))
                .forEach(card -> {
                    if(changeCardUserRequest.getAdd() != null){
                        card.getUserIds().addAll(changeCardUserRequest.getAdd());
                    }
                    if(changeCardUserRequest.getRemove() != null){
                        card.getUserIds().removeIf(id -> changeCardUserRequest.getRemove().contains(id));
                    }
                });
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }
}
