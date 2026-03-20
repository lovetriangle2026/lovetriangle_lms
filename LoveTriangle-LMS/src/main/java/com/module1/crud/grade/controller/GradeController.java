package com.module1.crud.grade.controller;

import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.service.GradeService;

import java.util.List;
import com.module1.crud.grade.model.dto.GradeEditDTO;
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
}
