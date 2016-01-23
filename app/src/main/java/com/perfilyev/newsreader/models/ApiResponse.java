package com.perfilyev.newsreader.models;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    private List<Article> data = new ArrayList<>();
    private List<Spotlight> spotlight = new ArrayList<>();

    public List<Article> getData() {
        return data;
    }

    public List<Spotlight> getSpotlight() {
        return spotlight;
    }
}
