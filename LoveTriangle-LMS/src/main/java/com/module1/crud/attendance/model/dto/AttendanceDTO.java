package com.module1.crud.attendance.model.dto;

import java.sql.Timestamp;

public class AttendanceDTO {

    private int id;
    private int studentId;
    private int sessionId;
    private String attendanceStatus;
    private Timestamp checkedAt;

    // 조회용 확장 필드
    private String studentName;
    private int courseId;
    private String courseTitle;
    private String sessionTitle;
    private int week;

    public AttendanceDTO() {
    }

    public AttendanceDTO(int id, int studentId, int sessionId, String attendanceStatus, Timestamp checkedAt, String studentName, int courseId, String courseTitle, String sessionTitle, int week) {
        this.id = id;
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.attendanceStatus = attendanceStatus;
        this.checkedAt = checkedAt;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.sessionTitle = sessionTitle;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public Timestamp getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(Timestamp checkedAt) {
        this.checkedAt = checkedAt;
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

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", sessionId=" + sessionId +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                ", checkedAt=" + checkedAt +
                ", studentName='" + studentName + '\'' +
                ", courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", sessionTitle='" + sessionTitle + '\'' +
                ", week=" + week +
                '}';
    }
}
