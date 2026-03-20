package com.module1.crud.attendance.model.dao;

import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    private final Connection connection;

    public AttendanceDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * 전체 출결 조회
     */
    public List<AttendanceDTO> findAll() throws SQLException {

        String query = QueryUtil.getQuery("attendance.findAll");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    /**
     * 강의별 출결 조회
     */
    public List<AttendanceDTO> findByCourseIdAndProfessorId(int courseId, int professorId) throws SQLException {
        String query = QueryUtil.getQuery("attendance.findByCourseIdAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    // -----------------------------------------------------------------------------------
    public List<ProfessorCourseDTO> findCoursesByProfessorId(int professorId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findCoursesByProfessorId");
        List<ProfessorCourseDTO> courseList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                ProfessorCourseDTO course = new ProfessorCourseDTO();
                course.setId(rset.getInt("id"));
                course.setTitle(rset.getString("title"));

                courseList.add(course);
            }
        }

        return courseList;
    }

    /**
     * 주차별 출결 조회
     */
    public List<AttendanceDTO> findByWeek(int week) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByWeek");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, week);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    // -----------------------------------------------------------------------------------
    public List<AttendanceDTO> findByWeekAndProfessorId(int week, int professorId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByWeekAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, week);
            pstmt.setInt(2, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    /**
     * 출결 유형별 조회
     */
    public List<AttendanceDTO> findByStatus(String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStatus");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }
    /**
     * 학생별 출결 조회
     */
    public List<AttendanceDTO> findByStudentId(int studentId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStudentId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    public List<AttendanceDTO> findByCourseIdAndWeekAndProfessorId(int courseId, int professorId, int week) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByCourseIdAndWeekAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, professorId);
            pstmt.setInt(3, week);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    public List<AttendanceDTO> findByCourseIdAndStatusAndProfessorId(int courseId, int professorId, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByCourseIdAndStatusAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, professorId);
            pstmt.setString(3, status);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    public List<ProfessorCourseDTO> findCoursesByStudentId(int studentId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findCoursesByStudentId");
        List<ProfessorCourseDTO> courseList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                ProfessorCourseDTO course = new ProfessorCourseDTO();
                course.setId(rset.getInt("id"));
                course.setTitle(rset.getString("title"));

                courseList.add(course);
            }
        }

        return courseList;
    }

    public List<AttendanceDTO> findByStudentIdAndCourseId(int studentId, int courseId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStudentIdAndCourseId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("attendance_id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setStudentName(rset.getString("student_name"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setCourseId(rset.getInt("course_id"));
                attendance.setCourseTitle(rset.getString("course_title"));
                attendance.setSessionTitle(rset.getString("session_title"));
                attendance.setWeek(rset.getInt("week"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

}