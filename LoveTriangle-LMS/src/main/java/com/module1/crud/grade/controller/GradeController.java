package com.module1.crud.grade.controller;

import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.service.GradeService;

import java.util.List;
import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;
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

    public List<GradeViewDTO> viewallgradeByprofessor(long professor) {
        return service.getSallgradeByprofessor(professor);

    }

    // 수정 대상 목록 조회
    public List<GradeEditDTO> getEditableGradeList(long professorId) {
        return service.getEditableGradeList(professorId);
    }

    // 중간 수정
    public int updateMidtermScore(int studentId, int courseId, int newScore) {
        return service.updateMidtermScore(studentId, courseId, newScore);
    }

    // 기말 수정
    public int updateFinalScore(int studentId, int courseId, int newScore) {
        return service.updateFinalScore(studentId, courseId, newScore);
    }

    // 과제 수정
    public int updateAssignmentScore(int studentId, int courseId, int newScore) {
        return service.updateAssignmentScore(studentId, courseId, newScore);
    }
    public List<GradeRegisterDTO> getAssignmentRegisterTargets(long professorId) {
        return service.getAssignmentRegisterTargets(professorId);
    }

    public int registerAssignmentScore(int studentId, int courseId, int score) {
        return service.registerAssignmentScore(studentId, courseId, score);
    }
    public List<GradeRegisterDTO> getMidtermRegisterTargets(long professorId) {
        return service.getMidtermRegisterTargets(professorId);
    }

    public int registerMidtermScore(int studentId, int courseId, int score) {
        return service.registerMidtermScore(studentId, courseId, score);
    }

    public List<GradeRegisterDTO> getFinalRegisterTargets(long professorId) {
        return service.getFinalRegisterTargets(professorId);
    }

    public int registerFinalScore(int studentId, int courseId, int score) {
        return service.registerFinalScore(studentId, courseId, score);
    }
}
