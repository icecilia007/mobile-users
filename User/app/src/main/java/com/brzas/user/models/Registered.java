package com.brzas.user.models;

import java.io.Serializable;

public class Registered implements Serializable {
    private String date;
    private int age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Registered{" +
                "date='" + date + '\'' +
                ", age=" + age +
                '}';
    }
}

