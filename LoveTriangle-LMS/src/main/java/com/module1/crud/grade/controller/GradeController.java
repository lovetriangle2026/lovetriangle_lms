package com.module1.crud.grade.controller;

import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.service.GradeService;

public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {

        this.service = service;
    }

    public List<GradeViewDTO> findAllCourses() {

        return service.findAllGrade();
    }
}
