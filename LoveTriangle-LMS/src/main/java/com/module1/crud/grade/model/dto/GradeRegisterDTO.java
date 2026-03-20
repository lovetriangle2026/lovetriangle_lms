package com.module1.crud.grade.model.dto;

public class GradeRegisterDTO {
    private int studentId;
    private String studentName;
    private int courseId;
    private String courseTitle;

    public GradeRegisterDTO(int studentId, String studentName, int courseId, String courseTitle) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    @Override
    public String toString() {
        return "등록대상{" +
                "학생ID=" + studentId +
                ", 학생이름='" + studentName + '\'' +
                ", 과목ID=" + courseId +
                ", 과목명='" + courseTitle + '\'' +
                '}';
    }
}
