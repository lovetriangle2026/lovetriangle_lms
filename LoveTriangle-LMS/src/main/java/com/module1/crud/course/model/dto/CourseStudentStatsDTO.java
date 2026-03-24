package com.module1.crud.course.model.dto;

import java.util.List;

public class CourseStudentStatsDTO {
    private int studentId;
    private String studentName;
    private int isHeartPublic; // 0: 비공개, 1: 공개
    private int totalHearts;
    private List<String> top3Tags;

    public CourseStudentStatsDTO(int studentId, String studentName, int isHeartPublic, int totalHearts, List<String> top3Tags) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.isHeartPublic = isHeartPublic;
        this.totalHearts = totalHearts;
        this.top3Tags = top3Tags;
    }

    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getIsHeartPublic() { return isHeartPublic; }
    public int getTotalHearts() { return totalHearts; }
    public List<String> getTop3Tags() { return top3Tags; }
}