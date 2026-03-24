package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class StudentTeamAssignmentDTO {
    private Long id; // team_assignment_id
    private String courseTitle;
    private String title;
    private Timestamp deadline;

    public StudentTeamAssignmentDTO(Long id, String courseTitle, String title, Timestamp deadline) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.title = title;
        this.deadline = deadline;
    }

    public Long getId() { return id; }
    public String getCourseTitle() { return courseTitle; }
    public String getTitle() { return title; }
    public Timestamp getDeadline() { return deadline; }
}