package com.ruslan.backendtrello.repository;

import com.ruslan.backendtrello.models.mongo.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card, Long> {
}
