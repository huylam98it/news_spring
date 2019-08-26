package com.foss.news.dto;

import java.io.Serializable;

public class ResponseModel implements Serializable {

    private String message;

    public ResponseModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
