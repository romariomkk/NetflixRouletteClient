package com.romariomkk.netflixrouletteclient.model;

/**
 * Created by romariomkk on 22.10.2016.
 */
public enum Properties {
    TITLE("title"),
    DIRECTOR("director");

    public String get() {
        return property;
    }

    String property;
    Properties(String property) {
        this.property = property;
    }

}