package com.module1.crud.assignments.model.dto;

// 2. TeamMemberDTO.java
public class TeamMemberDTO {
    private Long studentId;
    private String name;
    // 생성자, Getter/Setter 생략


    public TeamMemberDTO(Long studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public Long getStudentId() {

        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}