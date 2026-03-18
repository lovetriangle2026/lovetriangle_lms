package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class AssignmentDTO {

    private Long id;
    private Long course_Id;
    private String title;
    private String description;
    private Long deadline;

    public AssignmentDTO(long id, long courseId, String title, String description, Timestamp deadline) {
    }

    public AssignmentDTO(Long id, Long course_Id, String title, String description, Long deadline) {
        this.id = id;
        this.course_Id = course_Id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(Long course_Id) {
        this.course_Id = course_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "AssignmentDTO{" +
                "id=" + id +
                ", course_Id=" + course_Id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
