package me.rei_m.statepatternsample.entity;

import java.io.Serializable;

public class AtndEventEntity implements Serializable {

    public AtndEventEntity(int id, String title) {
        this.id = id;
        this.title = title;
    }

    private int id;

    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

