package com.foss.news.dto;

import java.io.Serializable;

public class PostModel implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
