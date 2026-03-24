package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class ProfessorAssignmentDTO {

    private Long assignmentId;
    private Long courseId;
    private String courseTitle;
    private String assignmentTitle;
    private String description;
    private Timestamp deadline;

    public ProfessorAssignmentDTO(Long assignmentId, Long courseId, String courseTitle,
                                  String assignmentTitle, String description, Timestamp deadline) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.assignmentTitle = assignmentTitle;
        this.description = description;
        this.deadline = deadline;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "ProfessorAssignmentDTO{" +
                "assignmentId=" + assignmentId +
                ", courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", assignmentTitle='" + assignmentTitle + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}