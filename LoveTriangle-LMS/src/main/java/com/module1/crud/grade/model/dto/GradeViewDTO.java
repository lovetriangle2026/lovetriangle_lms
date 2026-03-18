package com.module1.crud.grade.model.dto;

public class GradeViewDTO {
    private int studentId;
    private String studentName;
    private int course_id;
    private String assignment_title;
    private int midterm_score;
    private int final_score;
    private double midterm_35;
    private double final_35;
    private double attendance_score;
    private int assignment_score;
    private double total_score;
    private String grade;


    public GradeViewDTO(int studentId, String studentName, int course_id, String assignment_title, int midterm_score, int final_score, double midterm_35, double final_35, double attendance_score, int assignment_score, double total_score, String grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course_id = course_id;
        this.assignment_title = assignment_title;
        this.midterm_score = midterm_score;
        this.final_score = final_score;
        this.midterm_35 = midterm_35;
        this.final_35 = final_35;
        this.attendance_score = attendance_score;
        this.assignment_score = assignment_score;
        this.total_score = total_score;
        this.grade = grade;
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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getAssignment_title() {
        return assignment_title;
    }

    public void setAssignment_title(String assignment_title) {
        this.assignment_title = assignment_title;
    }

    public int getMidterm_score() {
        return midterm_score;
    }

    public void setMidterm_score(int midterm_score) {
        this.midterm_score = midterm_score;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }

    public double getMidterm_35() {
        return midterm_35;
    }

    public void setMidterm_35(double midterm_35) {
        this.midterm_35 = midterm_35;
    }

    public double getFinal_35() {
        return final_35;
    }

    public void setFinal_35(double final_35) {
        this.final_35 = final_35;
    }

    public double getAttendance_score() {
        return attendance_score;
    }

    public void setAttendance_score(double attendance_score) {
        this.attendance_score = attendance_score;
    }

    public int getAssignment_score() {
        return assignment_score;
    }

    public void setAssignment_score(int assignment_score) {
        this.assignment_score = assignment_score;
    }

    public double getTotal_score() {
        return total_score;
    }

    public void setTotal_score(double total_score) {
        this.total_score = total_score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "성적{" +
                "학번=" + studentId +
                ", 학생이름='" + studentName + '\'' +
                ", 과목아이디=" + course_id +
                ", 과목이름='" + assignment_title + '\'' +
                ", 중간고사=" + midterm_score +
                ", 기말고사=" + final_score +
                ", 중간고사(35만점)=" + midterm_35 +
                ", 기말고사(35만점)=" + final_35 +
                ", 출석점수=" + attendance_score +
                ", 과제점수=" + assignment_score +
                ", 총점=" + total_score +
                ", 등급= '" + grade + '\'' +
                '}';
    }
}
