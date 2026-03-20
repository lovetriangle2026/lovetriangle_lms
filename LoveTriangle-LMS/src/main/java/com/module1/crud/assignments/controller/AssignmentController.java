package com.module1.crud.assignments.controller;

import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.service.AssignmentService;

import java.util.List;

public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;


    }
    // ================================= 학생 파트 =======================================
    // ==================== 과제 조회 ========================
    public List<StudentAssignmentDTO> findMyAssignments(Long userId) {
        return service.findMyAssignments(userId);

    }

    // ==================== 과제 제출 ========================
    public boolean canSubmitAssignment(Long assignmentId, Long studentId) {
        return service.canSubmitAssignment(assignmentId, studentId);
    }

    public boolean isAlreadySubmitted(Long assignmentId, Long studentId) {
        return service.isAlreadySubmitted(assignmentId, studentId);
    }

    public void createSubmission(StudentAssignmentSubmissionDTO submissionDTO) {
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

    // ============================= 교수 파트 =================================
    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Long professorId) {
        return service.findAssignmentsByProfessor(professorId);
    }

    public boolean existsProfessorAssignment(Long assignmentId, Long professorId) {
        return service.existsProfessorAssignment(assignmentId, professorId);
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Long assignmentId, Long professorId) {
        return service.findSubmissionStatusByAssignment(assignmentId, professorId);
    }

}