package com.module1.crud.attendance.controller;

import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.attendance.model.service.AttendanceService;

import java.util.List;

public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    /**
     * 전체 출결 조회
     */
    public List<AttendanceDTO> findAllAttendance() {
        return service.findAllAttendance();
    }

    /**
     * 강의별 출결 조회
     */
    public List<ProfessorCourseDTO> findCoursesByProfessorId(int professorId) {
        return service.findCoursesByProfessorId(professorId);
    }
    public List<AttendanceDTO> findAttendanceByCourseId(int courseId, int professorId) {
        return service.findAttendanceByCourseId(courseId, professorId);
    }

    /**
     * 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByWeek(int week) {
        return service.findAttendanceByWeek(week);
    }

    /**
     * 출결 상태별 조회
     */
    public List<AttendanceDTO> findAttendanceByStatus(String status) {
        return service.findAttendanceByStatus(status);
    }

    /**
     * 학생별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByStudentId(int studentId) {
        return service.findAttendanceByStudentId(studentId);
    }
}