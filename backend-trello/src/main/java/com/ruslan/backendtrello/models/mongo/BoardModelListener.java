package com.ruslan.backendtrello.models.mongo;

import com.ruslan.backendtrello.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardModelListener extends AbstractMongoEventListener<Board> {
    private final SequenceGeneratorService sequenceGeneratorService;
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Board> event) {
        if(event.getSource().getId() == null){
            event.getSource().setId(sequenceGeneratorService.generateSequence(Board.SEQUENCE_NAME));
        }
    }
}
