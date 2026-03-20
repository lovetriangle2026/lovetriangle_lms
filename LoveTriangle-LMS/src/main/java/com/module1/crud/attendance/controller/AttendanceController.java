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
     * 교수 담당 강의 목록 조회
     */
    public List<ProfessorCourseDTO> findCoursesByProfessorId(int professorId) {
        return service.findCoursesByProfessorId(professorId);
    }

    /**
     * 강의별 전체 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseId(int courseId, int professorId) {
        return service.findAttendanceByCourseId(courseId, professorId);
    }

    /**
     * 강의 + 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseIdAndWeek(int courseId, int professorId, int week) {
        return service.findAttendanceByCourseIdAndWeek(courseId, professorId, week);
    }

    /**
     * 강의 + 출결 상태별 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseIdAndStatus(int courseId, int professorId, String status) {
        return service.findAttendanceByCourseIdAndStatus(courseId, professorId, status);
    }

    /**
     * 기존 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByWeek(int week) {
        return service.findAttendanceByWeek(week);
    }

    public List<AttendanceDTO> findAttendanceByWeek(int week, int professorId) {
        return service.findAttendanceByWeek(week, professorId);
    }

    /**
     * 기존 출결 상태별 조회
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

    public List<ProfessorCourseDTO> findCoursesByStudentId(int studentId) {
        return service.findCoursesByStudentId(studentId);
    }

    public List<AttendanceDTO> findAttendanceByStudentIdAndCourseId(int studentId, int courseId) {
        return service.findAttendanceByStudentIdAndCourseId(studentId, courseId);
    }
}