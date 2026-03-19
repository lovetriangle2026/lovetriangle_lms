package com.module1.crud.grade.controller;

import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.service.GradeService;

import java.util.List;

public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {

        this.service = service;
    }

    public List<GradeViewDTO> findAllGrade(long studentId) {

        return service.findAllGrade(studentId);
    }

    public List<GradeViewDTO> handlefindgrade(long professeorid,String studentname) {

        return service.getStudentgrade(professeorid,studentname);
    }
}
