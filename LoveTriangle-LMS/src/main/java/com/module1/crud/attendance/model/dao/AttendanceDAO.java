package com.module1.crud.attendance.model.dao;

import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.attendance.model.dto.SessionDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // 💡 DAO는 가볍게! 필드와 생성자에서 Connection을 지웁니다.
    public AttendanceDAO() {
    }

    /**
     * 전체 출결 조회
     */
    public List<AttendanceDTO> findAll(Connection con) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findAll");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<AttendanceDTO> findByCourseIdAndProfessorId(Connection con, int courseId, int professorId) throws SQLException {
        String query = QueryUtil.getQuery("attendance.findByCourseIdAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<ProfessorCourseDTO> findCoursesByProfessorId(Connection con, int professorId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findCoursesByProfessorId");
        List<ProfessorCourseDTO> courseList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<AttendanceDTO> findByWeek(Connection con, int week) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByWeek");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<AttendanceDTO> findByWeekAndProfessorId(Connection con, int week, int professorId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByWeekAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<AttendanceDTO> findByStatus(Connection con, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStatus");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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
    public List<AttendanceDTO> findByStudentId(Connection con, int studentId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStudentId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    public List<AttendanceDTO> findByCourseIdAndWeekAndProfessorId(Connection con, int courseId, int professorId, int week) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByCourseIdAndWeekAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    public List<AttendanceDTO> findByCourseIdAndStatusAndProfessorId(Connection con, int courseId, int professorId, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByCourseIdAndStatusAndProfessorId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    public List<ProfessorCourseDTO> findCoursesByStudentId(Connection con, int studentId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findCoursesByStudentId");
        List<ProfessorCourseDTO> courseList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    public List<AttendanceDTO> findByStudentIdAndCourseId(Connection con, int studentId, int courseId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStudentIdAndCourseId");
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    /**
     * 교수가 학생 출결 수정
     * */
    public boolean updateAttendanceStatus(Connection con, int attendanceId, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.updateAttendanceStatus");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, attendanceId);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * 학생 출석체크
     * */
    public List<SessionDTO> findAvailableSessionByStudentId(Connection con, int studentId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findAvailableSessionByStudentId");
        List<SessionDTO> sessionList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                SessionDTO session = new SessionDTO();
                session.setId(rset.getInt("session_id"));
                session.setCourseId(rset.getInt("course_id"));
                session.setCourseTitle(rset.getString("course_title"));
                session.setWeek(rset.getInt("week"));
                session.setStartAt(rset.getTimestamp("start_at"));
                session.setEndAt(rset.getTimestamp("end_at"));

                sessionList.add(session);
            }
        }

        return sessionList;
    }

    public AttendanceDTO findByStudentIdAndSessionId(Connection con, int studentId, int sessionId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findByStudentIdAndSessionId");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sessionId);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setId(rset.getInt("id"));
                attendance.setStudentId(rset.getInt("student_id"));
                attendance.setSessionId(rset.getInt("session_id"));
                attendance.setAttendanceStatus(rset.getString("attendance_status"));
                attendance.setCheckedAt(rset.getTimestamp("checked_at"));
                return attendance;
            }
        }

        return null;
    }

    public boolean insertAttendance(Connection con, int studentId, int sessionId, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.insertAttendance");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sessionId);
            pstmt.setString(3, status);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateAttendanceCheck(Connection con, int attendanceId, String status) throws SQLException {

        String query = QueryUtil.getQuery("attendance.updateAttendanceCheck");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, attendanceId);

            return pstmt.executeUpdate() > 0;
        }
    }
    public List<SessionDTO> findSessionsByCourseId(Connection con, int courseId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.findSessionsByCourseId");
        List<SessionDTO> sessionList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, courseId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                SessionDTO session = new SessionDTO();
                session.setId(rset.getInt("session_id"));
                session.setCourseId(rset.getInt("course_id"));
                session.setWeek(rset.getInt("week"));
                session.setStartAt(rset.getTimestamp("start_at"));
                session.setEndAt(rset.getTimestamp("end_at"));

                sessionList.add(session);
            }
        }

        return sessionList;
    }

    public int updateToExcusedPending(Connection con, int studentId, int sessionId) throws SQLException {

        String query = QueryUtil.getQuery("attendance.updateToExcusedPending");
        System.out.println("🔥 쿼리 확인 = [" + query + "]");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sessionId);

            return pstmt.executeUpdate();
        }
    }

    /**
     * 현재 세션까지의 출석 상태를 최신순으로 가져옵니다.
     * 예: PRESENT, PRESENT, ABSENT ...
     */
    public List<String> getAttendanceStatusesDesc(Connection con, int studentId, int courseId, int currentSessionId) throws SQLException {
        String query =
                "SELECT a.attendance_status " +
                        "FROM attendance a " +
                        "JOIN sessions s ON a.session_id = s.id " +
                        "WHERE a.student_id = ? " +
                        "  AND s.course_id = ? " +
                        "  AND s.start_at <= (SELECT start_at FROM sessions WHERE id = ?) " +
                        "ORDER BY s.start_at DESC";

        List<String> statusList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, currentSessionId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    statusList.add(rset.getString("attendance_status"));
                }
            }
        }

        return statusList;
    }

    /**
     * 최신순 출석 기록을 바탕으로 연속 출석 streak 계산
     * PRESENT만 연속 출석으로 인정
     */
    public int getAttendanceStreak(Connection con, int studentId, int courseId, int currentSessionId) throws SQLException {
        List<String> statusList = getAttendanceStatusesDesc(con, studentId, courseId, currentSessionId);





        int streak = 0;
        for (String status : statusList) {
            if ("PRESENT".equals(status)) {
                streak++;
            } else {
                break;
            }
        }

        return streak;
    }

}