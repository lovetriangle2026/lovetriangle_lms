package com.module1.crud.grade.model.dto;

public class GradeEditDTO {
    private int studentId;
    private String studentName;
    private int courseId;
    private String courseTitle;
    private int midtermScore;
    private int finalScore;
    private int assignmentScore;


    public GradeEditDTO(int studentId, String studentName, int courseId, String courseTitle, int midtermScore, int finalScore, int assignmentScore) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.assignmentScore = assignmentScore;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(int midtermScore) {
        this.midtermScore = midtermScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getAssignmentScore() {
        return assignmentScore;
    }

    public void setAssignmentScore(int assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    @Override
    public String toString() {
        return "GradeEditDTO{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", midtermScore=" + midtermScore +
                ", finalScore=" + finalScore +
                ", assignmentScore=" + assignmentScore +
                '}';
    }
}