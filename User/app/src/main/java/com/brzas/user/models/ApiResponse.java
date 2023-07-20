package com.brzas.user.models;

import android.icu.text.IDNA;

import java.util.List;

public class ApiResponse {
    private List<User> results;
    private IDNA.Info info;

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public IDNA.Info getInfo() {
        return info;
    }

    public void setInfo(IDNA.Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "results=" + results +
                ", info=" + info +
                '}';
    }
}
