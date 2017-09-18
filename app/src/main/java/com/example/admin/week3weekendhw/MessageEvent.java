package com.example.admin.week3weekendhw;

import com.example.admin.week3weekendhw.modelrepo.ModelResponse;

import java.util.List;

/**
 * Created by Admin on 9/16/2017.
 */


public class MessageEvent {
    List<ModelResponse> message;
    

    public MessageEvent(List<ModelResponse> a) {
        this.message = a;

    }

    public List<ModelResponse> getMessage() {
        return message;
    }

    public void setMessage(List<ModelResponse> message) {
        this.message = message;
    }
}