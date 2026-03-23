package com.module1.crud.assignments.model.dto;

// 1. HeartTagDTO.java
public class HeartTagDTO {
    private int id;
    private String category;
    private String tagName;
    private String description;

    public HeartTagDTO(int id, String category, String tagName, String description) {
        this.id = id;
        this.category = category;
        this.tagName = tagName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // 생성자, Getter/Setter 생략 (모든 필드 포함되게 작성해주세요)
}

