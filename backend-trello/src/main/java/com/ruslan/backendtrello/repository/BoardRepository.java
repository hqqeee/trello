package com.ruslan.backendtrello.repository;

import com.ruslan.backendtrello.models.mongo.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, Long> {
}
