package com.module1.crud.assignments.controller;

import com.module1.crud.assignments.model.dto.*;
import com.module1.crud.assignments.model.service.AssignmentService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    public boolean isAssignmentDeadlinePassed(Long assignmentId) {
        return service.isAssignmentDeadlinePassed(assignmentId);
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
    // =============== 과제 조회 =================
    public Map<Long, String> findProfessorCourses(Long professorId) {
        return service.findProfessorCourses(professorId);
    }

    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Long professorId) {
        return service.findAssignmentsByProfessor(professorId);
    }

    public boolean existsProfessorAssignment(Long assignmentId, Long professorId) {
        return service.existsProfessorAssignment(assignmentId, professorId);
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Long assignmentId, Long professorId) {
        return service.findSubmissionStatusByAssignment(assignmentId, professorId);
    }

    // ============== 과제 생성 ===================
    public boolean existsProfessorCourse(Long courseId, Long professorId) {
        return service.existsProfessorCourse(courseId, professorId);
    }

    public boolean existsAssignmentByCourse(Long courseId) {
        return service.existsAssignmentByCourse(courseId);
    }

    public void createAssignment(Long courseId, String title, String description,
                                 Timestamp deadline) {
        service.createAssignment(courseId, title, description, deadline);
    }

    // =============== 과제 수정 ===================
    public void updateProfessorAssignment(Long assignmentId, Long professorId,
                                          String newTitle, String newDescription,
                                          java.sql.Timestamp newDeadline) {
        service.updateProfessorAssignment(assignmentId, professorId, newTitle, newDescription, newDeadline);
    }

    // ================= 과제 삭제 ===================
    public void deleteProfessorAssignment(Long assignmentId, Long professorId) {
        service.deleteProfessorAssignment(assignmentId, professorId);
    }
    public SubmissionRankResultDTO createSubmission(StudentAssignmentSubmissionDTO submissionDTO, boolean late) {
        return service.createSubmission(submissionDTO, late);
    }
}