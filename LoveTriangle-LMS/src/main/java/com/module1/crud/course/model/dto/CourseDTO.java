package com.module1.crud.course.model.dto;

public class CourseDTO {
    private Long id;
    private String course_code;
    private int professor_id;
    private String title;
    private String description;
    private String semester;

    public CourseDTO(Long id, String course_code, int professor_id, String title, String description, String semester) {
        this.id = id;
        this.course_code = course_code;
        this.professor_id = professor_id;
        this.title = title;
        this.description = description;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", course_code='" + course_code + '\'' +
                ", professor_id=" + professor_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}
