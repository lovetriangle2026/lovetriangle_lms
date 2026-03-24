package com.module1.crud.attendance.model.dto;

public class ProfessorCourseDTO {

    private int id;
    private String title;

    public ProfessorCourseDTO() {
    }

    public ProfessorCourseDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}