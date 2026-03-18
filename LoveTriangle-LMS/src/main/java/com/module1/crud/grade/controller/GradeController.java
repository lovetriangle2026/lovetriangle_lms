package com.module1.crud.grade.controller;

public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {

        this.service = service;
    }

    public List<GradeViewDTO> findAllCourses() {

        return service.findAllGrade();
    }
}
