package com.fb.demo.entity;

import java.io.Serializable;
import lombok.Getter;

@Getter
public enum Gender implements Serializable {
    MALE("male"),

    FEMALE("female");

    private String messsage;

    Gender(String message) {
        this.messsage = message;
    }

}
