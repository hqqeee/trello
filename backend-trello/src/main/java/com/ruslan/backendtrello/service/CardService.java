package com.ruslan.backendtrello.service;

import com.ruslan.backendtrello.models.mongo.Board;
import com.ruslan.backendtrello.models.mongo.Card;
import com.ruslan.backendtrello.models.mongo.List;
import com.ruslan.backendtrello.payload.request.card.CreateCardRequest;
import com.ruslan.backendtrello.payload.response.CreatedResponse;
import com.ruslan.backendtrello.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    @Value("${trello.card.defaultColor}")
    private static String defaultColor;
    private final BoardRepository boardRepository;
    public CreatedResponse addCard(CreateCardRequest createCardRequest, Board board, Long userId) {
        Long currentCardsNumber = board.getLists().stream().map(list -> (long) list.getCards().size()).reduce(0L, Long::sum);
        Card card = new Card(
            currentCardsNumber,
                createCardRequest.getTitle(),
                createCardRequest.getDescription(),
                defaultColor,
                createCardRequest.getCustom(),
                Collections.singletonList(userId),
                LocalDateTime.now());
        Optional<List> list = board.getLists().stream()
                .filter(l -> Objects.equals(l.getId(), createCardRequest.getListId()))
                .findFirst();
        System.out.println(list);
        if(list.isPresent()){
            list.get().getCards().add(card);
            boardRepository.save(board);
        }
        return new CreatedResponse("Created", card.getId());
    }
}
