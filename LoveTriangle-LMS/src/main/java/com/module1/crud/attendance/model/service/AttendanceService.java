package com.module1.crud.attendance.model.service;

import com.module1.crud.attendance.model.dao.AttendanceDAO;
import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AttendanceService {


    private final AttendanceDAO attendanceDAO;
    private final Connection connection;

    public AttendanceService(Connection connection) {
        this.attendanceDAO = new AttendanceDAO(connection);
        this.connection = connection;
    }

    /**
     * 전체 출결 조회
     */
    public List<AttendanceDTO> findAllAttendance() {
        try {
            return attendanceDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("출결 전체 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 강의별 출결 조회
     */
    public List<ProfessorCourseDTO> findCoursesByProfessorId(int professorId) {
        try {
            return attendanceDAO.findCoursesByProfessorId(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 강의 목록 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }
    public List<AttendanceDTO> findAttendanceByCourseId(int courseId, int professorId) {
        try {
            return attendanceDAO.findByCourseIdAndProfessorId(courseId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 강의별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByWeek(int week) {
        try {
            return attendanceDAO.findByWeek(week);
        } catch (SQLException e) {
            throw new RuntimeException("주차별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 출결 상태별 조회
     */
    public List<AttendanceDTO> findAttendanceByStatus(String status) {
        try {
            return attendanceDAO.findByStatus(status);
        } catch (SQLException e) {
            throw new RuntimeException("출결 상태별 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 학생별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByStudentId(int studentId) {
        try {
            return attendanceDAO.findByStudentId(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("학생별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }
}