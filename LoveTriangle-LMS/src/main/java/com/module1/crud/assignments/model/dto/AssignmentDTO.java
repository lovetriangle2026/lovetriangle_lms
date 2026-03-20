package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class AssignmentDTO {

    private Long id;
    private Long course_Id;
    private String title;
    private String description;
    private Timestamp deadline;
    private String submissionStatus;
    private String submissionContent;
    private Timestamp submittedAt;

    public AssignmentDTO(long id, long course_Id, String title, String description,
                         Timestamp deadline, String submissionStatus, String submissionContent, Timestamp submittedAt) {
        this.id = id;
        this.course_Id = course_Id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.submissionStatus = submissionStatus;
        this.submissionContent = submissionContent;
        this.submittedAt = submittedAt;

    }

    public AssignmentDTO(Long id, Long course_Id, String title, String description, Timestamp deadline) {
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

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public String getSubmissionContent() {
        return submissionContent;
    }

    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "AssignmentDTO{" +
                "id=" + id +
                ", course_Id=" + course_Id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", submissionStatus='" + submissionStatus + '\'' +
                ", submissionContent='" + submissionContent + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
