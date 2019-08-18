package com.damo.mongodbfeedback.dao;

import com.damo.mongodbfeedback.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedBackRepository extends MongoRepository<Feedback, String> {

}
