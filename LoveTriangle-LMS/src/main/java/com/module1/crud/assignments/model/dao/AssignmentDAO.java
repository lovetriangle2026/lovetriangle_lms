package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssignmentDAO {

    // 💡 더 이상 Connection을 필드로 가지지 않습니다.
    public AssignmentDAO() {
    }

    // ========================================학생 파트 ===========================================
    // ======================== 과제 조회 =========================
    public List<StudentAssignmentDTO> findMyAssignments(Connection con, Long userId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.findMyAssignments");
        List<StudentAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    StudentAssignmentDTO assignment = new StudentAssignmentDTO(
                            rset.getLong("id"),
                            rset.getLong("course_id"),
                            rset.getString("course_title"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getTimestamp("deadline"),
                            rset.getString("submission_status"),
                            rset.getString("submission_content"),
                            rset.getTimestamp("submitted_at")
                    );

                    assignmentList.add(assignment);
                }
            }
        }

        return assignmentList;
    }

    // ============================ 과제 제출 ============================
    public boolean existsMyAssignment(Connection con, Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.existsMyAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, studentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    // 제출할 때 마감일이 지났는지 확인하기 위한 코드
    public Timestamp findAssignmentDeadline(Connection con, Long assignmentId) throws SQLException {
        String query = "SELECT deadline FROM assignments WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getTimestamp("deadline");
                }
            }
        }

        return null;
    }


    // ====================================== 교수 파트 ========================================
    // ================= 과제 조회 ====================
    public Map<Long, String> findProfessorCourses(Connection con, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findProfessorCourses");
        Map<Long, String> courseMap = new LinkedHashMap<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    courseMap.put(
                            rset.getLong("course_id"),
                            rset.getString("course_title")
                    );
                }
            }
        }

        return courseMap;
    }

    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Connection con, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findAssignmentsByProfessor");
        List<ProfessorAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    ProfessorAssignmentDTO dto = new ProfessorAssignmentDTO(
                            rset.getLong("assignment_id"),
                            rset.getLong("course_id"),
                            rset.getString("course_title"),
                            rset.getString("assignment_title"),
                            rset.getString("description"),
                            rset.getTimestamp("deadline")
                    );
                    assignmentList.add(dto);
                }
            }
        }

        return assignmentList;
    }

    public boolean existsProfessorAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findSubmissionStatusByAssignment");
        List<ProfessorAssignmentSubmissionDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    ProfessorAssignmentSubmissionDTO dto = new ProfessorAssignmentSubmissionDTO(
                            rset.getLong("student_id"),
                            rset.getString("student_name"),
                            rset.getString("submission_status"),
                            rset.getString("content_title"),
                            rset.getTimestamp("submitted_at")
                    );
                    list.add(dto);
                }
            }
        }

        return list;
    }

    // ================== 과제 생성 ==================
    public boolean existsProfessorCourse(Connection con, Long courseId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorCourse");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsAssignmentByCourse(Connection con, Long courseId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsAssignmentByCourse");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int createAssignment(Connection con, Long courseId, String title, String description,
                                Timestamp deadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.create");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setTimestamp(4, deadline);

            return pstmt.executeUpdate();
        }
    }

    // ==================== 과제 수정 ==================
    public int updateProfessorAssignment(Connection con, Long assignmentId, Long professorId,
                                         String newTitle, String newDescription,
                                         java.sql.Timestamp newDeadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.update");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newDescription);
            pstmt.setTimestamp(3, newDeadline);
            pstmt.setLong(4, assignmentId);
            pstmt.setLong(5, professorId);

            return pstmt.executeUpdate();
        }
    }

    // ===================== 과제 삭제 ======================
    public int deleteProfessorAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.delete");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            return pstmt.executeUpdate();
        }
    }

    public int deleteSubmissionsByAssignment(Connection con, Long assignmentId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.deleteSubmissionsByAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            return pstmt.executeUpdate();
        }
    }
}