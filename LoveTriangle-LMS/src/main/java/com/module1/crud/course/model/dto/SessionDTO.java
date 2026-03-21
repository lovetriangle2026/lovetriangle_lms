package com.module1.crud.course.model.dto;
import java.sql.Timestamp;


public class SessionDTO {
    private int id;
    private int courseId;
    private String title;
    private Timestamp startAt;
    private Timestamp endAt;
    private int week;

    public SessionDTO(int id, int courseId, String title, Timestamp startAt, Timestamp endAt, int week) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public int getWeek() {
        return week;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", week=" + week +
                '}';
    }

}
