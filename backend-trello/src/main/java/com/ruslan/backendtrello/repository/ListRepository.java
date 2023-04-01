package com.ruslan.backendtrello.repository;

import com.ruslan.backendtrello.models.mongo.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends MongoRepository<List, Long> {
}
