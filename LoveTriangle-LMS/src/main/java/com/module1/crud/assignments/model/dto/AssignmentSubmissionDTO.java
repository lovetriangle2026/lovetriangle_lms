package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class AssignmentSubmissionDTO {

    private Long id;
    private Long assignmentId;
    private Long studentId;
    private String contentTitle;
    private Timestamp submittedAt;

    public AssignmentSubmissionDTO() {
    }

    public AssignmentSubmissionDTO(Long assignmentId, Long studentId, String contentTitle) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.contentTitle = contentTitle;
    }

    public AssignmentSubmissionDTO(Long id, Long assignmentId, Long studentId, String contentTitle, Timestamp submittedAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.contentTitle = contentTitle;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "AssignmentSubmissionDTO{" +
                "id=" + id +
                ", assignmentId=" + assignmentId +
                ", studentId=" + studentId +
                ", contentTitle='" + contentTitle + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}