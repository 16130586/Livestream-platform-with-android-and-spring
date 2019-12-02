package com.t4.androidclient.model.livestream.page;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class Page {
    @JsonAlias({"access_token"})
    private String accessToken;
    private String category;
    @JsonAlias({"category_list"})
    private List<Category> categoryList;
    private String name;
    private String id;
    private String[] tasks;

    public String toString() {
        return this.name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getTasks() {
        return tasks;
    }

    public void setTasks(String[] tasks) {
        this.tasks = tasks;
    }
}
