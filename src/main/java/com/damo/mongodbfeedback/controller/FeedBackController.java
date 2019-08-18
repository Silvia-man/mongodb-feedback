package com.damo.mongodbfeedback.controller;


import com.damo.mongodbfeedback.dao.FeedBackRepository;
import com.damo.mongodbfeedback.dao.IFeedBack;
import com.damo.mongodbfeedback.model.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final IFeedBack feedBackTemplate;

    private final FeedBackRepository feedBackRepository;


    private Feedback getWithDefault(String id) {
        return feedBackRepository.findById(id).orElse(null);

    }

    public FeedBackRepository getFeedBackRepository() {
        return feedBackRepository;
    }

    public FeedBackController(IFeedBack feedBackTemplate, FeedBackRepository feedBackRepository) {
        this.feedBackTemplate = feedBackTemplate;
        this.feedBackRepository = feedBackRepository;
    }

    @GetMapping
    public List<Feedback> getAll() {
        logger.info("fetch all feedback");
        return feedBackTemplate.getAll();
    }


    @GetMapping("/all")
    public List<Feedback> getAllFeedbacks() {
        logger.info("[JPA]fetching all feedback");
        return feedBackRepository.findAll();
    }


    @PostMapping
    public Feedback add(@RequestBody Feedback feedback) {
        logger.info("add feedback");
        return feedBackTemplate.add(feedback);

    }

    @PostMapping("/new")
    public Feedback addFeedBack(@RequestBody Feedback feedback) {
        logger.info("[JPA] add feedback");
        return feedBackRepository.save(feedback);
    }




    @GetMapping("/{id}")
    public Feedback getById (@PathVariable  String id) {
        logger.info("fetching feedback of " + id);
        return feedBackTemplate.getById(id);
    }


    @GetMapping("/id/{id}")
    public Feedback getFeedBackById(@PathVariable String id) {
        logger.info("[JPA] fetching feedback of" + id);
        return getWithDefault(id);
    }

    @GetMapping("/stats")
    public String analyse() {
        return feedBackTemplate.analyse();
    }


}
