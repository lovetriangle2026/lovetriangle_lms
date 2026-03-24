package com.module1.crud.grade.model.dto;

public class GradeViewDTO {
    private int studentId;
    private String studentName;
    private int course_id;
    private String course_title;
    private Integer midterm_score;
    private Integer final_score;
    private Double midterm_35;
    private Double final_35;
    private Double attendance_score;
    private Integer assignment_score;
    private Double total_score;
    private String grade;

    public GradeViewDTO(int studentId, String studentName, int course_id, String course_title,
                        Integer midterm_score, Integer final_score,
                        Double midterm_35, Double final_35,
                        Double attendance_score, Integer assignment_score,
                        Double total_score, String grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course_id = course_id;
        this.course_title = course_title;
        this.midterm_score = midterm_score;
        this.final_score = final_score;
        this.midterm_35 = midterm_35;
        this.final_35 = final_35;
        this.attendance_score = attendance_score;
        this.assignment_score = assignment_score;
        this.total_score = total_score;
        this.grade = grade;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getCourse_id() { return course_id; }
    public void setCourse_id(int course_id) { this.course_id = course_id; }

    public String getCourse_title() { return course_title; }
    public void setCourse_title(String course_title) { this.course_title = course_title; }

    public Integer getMidterm_score() { return midterm_score; }
    public void setMidterm_score(Integer midterm_score) { this.midterm_score = midterm_score; }

    public Integer getFinal_score() { return final_score; }
    public void setFinal_score(Integer final_score) { this.final_score = final_score; }

    public Double getMidterm_35() { return midterm_35; }
    public void setMidterm_35(Double midterm_35) { this.midterm_35 = midterm_35; }

    public Double getFinal_35() { return final_35; }
    public void setFinal_35(Double final_35) { this.final_35 = final_35; }

    public Double getAttendance_score() { return attendance_score; }
    public void setAttendance_score(Double attendance_score) { this.attendance_score = attendance_score; }

    public Integer getAssignment_score() { return assignment_score; }
    public void setAssignment_score(Integer assignment_score) { this.assignment_score = assignment_score; }

    public Double getTotal_score() { return total_score; }
    public void setTotal_score(Double total_score) { this.total_score = total_score; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "성적{" +
                "학번=" + studentId +
                ", 학생이름='" + studentName + '\'' +
                ", 과목아이디=" + course_id +
                ", 과목이름='" + course_title + '\'' +
                ", 중간고사=" + midterm_score +
                ", 기말고사=" + final_score +
                ", 중간고사(35만점)=" + midterm_35 +
                ", 기말고사(35만점)=" + final_35 +
                ", 출석점수=" + attendance_score +
                ", 과제점수=" + assignment_score +
                ", 총점=" + total_score +
                ", 등급='" + grade + '\'' +
                '}';
    }
}