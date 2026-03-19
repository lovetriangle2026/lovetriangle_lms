package com.module1.crud.assignments.controller;

import com.module1.crud.assignments.model.dto.AssignmentDTO;
import com.module1.crud.assignments.model.service.AssignmentService;

import java.util.List;

public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;


    }

    public List<AssignmentDTO> findMyAssignments(Long userId) {

        return service.findMyAssignments(userId);


    }
}
