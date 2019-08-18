package com.damo.mongodbfeedback.dao;

import com.damo.mongodbfeedback.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Repository
public class FeedBackImpl implements IFeedBack {
    private final String COLLECTION = "feedback";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Feedback> getAll() {
        return mongoTemplate.findAll(Feedback.class);

    }

    @Override
    public Feedback getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Feedback.class);
    }

    @Override
    public Feedback add(Feedback feedback){
        mongoTemplate.save(feedback);
        return feedback;
    }

    @Override
    public Object getAllSelections(String id) {
        return null;
    }

    @Override
    public String getSelection(String id, String key) {
        return null;
    }

    @Override
    public String AddSelection(String id, String key, String val) {
        return null;
    }

    @Override
    public String analyse() {

    //    final String map = "function() {emit(this.star, 1);}";
    //    final String reduce = "function(name, count) {return Array.sum(count)}";
    //    MapReduceResults<KeyValuePair> results = mongoTemplate.mapReduce (COLLECTION, map, reduce, KeyValuePair.class);

        MapReduceOptions options = MapReduceOptions.options();
        options.outputCollection("statsStar");
        options.outputTypeReduce();

        MapReduceResults<KeyValuePair> results = mongoTemplate.mapReduce(COLLECTION, "classpath:map.js", "classpath:reduce.js", options, KeyValuePair.class);


        StringBuilder stats = new StringBuilder();
        for (KeyValuePair result : results) {
            stats.append(result.toString());
        }
        return stats.toString();
    }

    public static class KeyValuePair implements Comparable<KeyValuePair> {

        String id;
        Long value;

        public KeyValuePair() {
        }

        public KeyValuePair(String id, Long value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Star: " + id + " Count: " + value + "; ";
        }

        @Override
        public int compareTo(KeyValuePair o) {
            if (this.value < o.value) {
                return 1;
            } else if (this.value > o.value) {
                return 0;
            } else {
                return 0;
            }
        }
    }
}
