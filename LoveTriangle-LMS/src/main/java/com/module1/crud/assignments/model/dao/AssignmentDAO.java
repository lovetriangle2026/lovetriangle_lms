package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.global.utils.QueryUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    private final Connection connection;

    public AssignmentDAO(Connection connection) {

        this.connection = connection;
    }
    // ========================================학생 파트 ===========================================
    // ======================== 과제 조회 =========================
    public List<StudentAssignmentDTO> findMyAssignments(Long userId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.findMyAssignments");
        List<StudentAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    StudentAssignmentDTO assignment = new StudentAssignmentDTO(
                            rset.getLong("id"),
                            rset.getLong("course_id"),
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
    public boolean existsMyAssignment(Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.existsMyAssignment");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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


    // ====================================== 교수 파트 ========================================
    // ================= 과제 조회 ====================
    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findAssignmentsByProfessor");
        List<ProfessorAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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

    public boolean existsProfessorAssignment(Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorAssignment");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findSubmissionStatusByAssignment");
        List<ProfessorAssignmentSubmissionDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
    public boolean existsProfessorCourse(Long courseId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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

    public boolean existsAssignmentByCourse(Long courseId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsAssignmentByCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int createAssignment(Long courseId, String title, String description,
                                Timestamp deadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.create");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setTimestamp(4, deadline);

            return pstmt.executeUpdate();
        }
    }

    // ==================== 과제 수정 ==================
    public int updateProfessorAssignment(Long assignmentId, Long professorId,
                                         String newTitle, String newDescription,
                                         java.sql.Timestamp newDeadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.update");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newDescription);
            pstmt.setTimestamp(3, newDeadline);
            pstmt.setLong(4, assignmentId);
            pstmt.setLong(5, professorId);

            return pstmt.executeUpdate();
        }
    }
}


