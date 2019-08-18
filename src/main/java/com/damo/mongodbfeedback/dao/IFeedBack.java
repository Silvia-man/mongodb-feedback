package com.damo.mongodbfeedback.dao;

import com.damo.mongodbfeedback.model.Feedback;

import java.util.List;

public interface IFeedBack {
    List<Feedback> getAll();
    Feedback getById(String id);
    Feedback add(Feedback feedback);
    Object getAllSelections(String id);
    String getSelection(String id, String key);
    String AddSelection(String id, String key, String val);
    String analyse();
}
