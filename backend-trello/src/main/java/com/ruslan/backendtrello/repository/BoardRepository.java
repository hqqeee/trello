package com.ruslan.backendtrello.repository;

import com.ruslan.backendtrello.models.mongo.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, Long> {
    public java.util.List<Board> findByOwnersIdContains(Long userId);
}
