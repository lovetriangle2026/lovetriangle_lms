package com.module1.crud.assignments.model.dto;

import java.sql.Timestamp;

public class ProfessorAssignmentSubmissionDTO {

    private Long studentId;
    private String studentName;
    private String submissionStatus;
    private String contentTitle;
    private Timestamp submittedAt;

    public ProfessorAssignmentSubmissionDTO(Long studentId, String studentName, String submissionStatus,
                                            String contentTitle, Timestamp submittedAt) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.submissionStatus = submissionStatus;
        this.contentTitle = contentTitle;
        this.submittedAt = submittedAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    @Override
    public String toString() {
        return "ProfessorAssignmentSubmissionDTO{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", submissionStatus='" + submissionStatus + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
