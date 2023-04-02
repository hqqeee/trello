package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.Card;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.payload.request.card.CreateCardRequest;
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

    public CreatedResponse addCard(CreateCardRequest createCardRequest, Board board, Long userId) {
        Long currentCardsNumber = board.getLists().stream().map(list -> (long) list.getCards().size()).reduce(0L, Long::sum);
        Card card = new Card(
                currentCardsNumber,
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
        System.out.println(board);
        boardRepository.save(board);
        return new MessageResponse("Updated");
    }
}
