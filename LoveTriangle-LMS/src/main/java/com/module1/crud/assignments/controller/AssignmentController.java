package com.module1.crud.assignments.controller;

import com.module1.crud.assignments.model.dto.AssignmentDTO;
import com.module1.crud.assignments.model.dto.AssignmentSubmissionDTO;
import com.module1.crud.assignments.model.service.AssignmentService;

import java.util.List;

public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;


    }

    // ==================== 과제 조회 ========================
    public List<AssignmentDTO> findMyAssignments(Long userId) {
        return service.findMyAssignments(userId);

    }

    // ==================== 과제 제출 ========================
    public boolean canSubmitAssignment(Long assignmentId, Long studentId) {
        return service.canSubmitAssignment(assignmentId, studentId);
    }

    public boolean isAlreadySubmitted(Long assignmentId, Long studentId) {
        return service.isAlreadySubmitted(assignmentId, studentId);
    }

    public void createSubmission(AssignmentSubmissionDTO submissionDTO) {
        service.createSubmission(submissionDTO);
    }


    // ====================== 과제 수정 ===========================
    public void updateSubmission(Long assignmentId, Long studentId, String newContent) {
        service.updateSubmission(assignmentId, studentId, newContent);
    }

    // ====================== 과제 삭제 ===========================
    public void deleteSubmission(Long assignmentId, Long studentId) {
        service.deleteSubmission(assignmentId, studentId);
    }

}