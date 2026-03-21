package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.StudentAssignmentSubmissionDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentSubmissionDAO {

    // 💡 더 이상 Connection을 필드로 가지지 않습니다.
    public AssignmentSubmissionDAO() {
    }

    // ======================= 과제 조회 ==========================
    public boolean existsByAssignmentAndStudent(Connection con, Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.existsByAssignmentAndStudent");

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

    // ====================== 과제 제출 ===========================
    public int createSubmission(Connection con, StudentAssignmentSubmissionDTO submissionDTO) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.create");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, submissionDTO.getAssignmentId());
            pstmt.setLong(2, submissionDTO.getStudentId());
            pstmt.setString(3, submissionDTO.getContentTitle());

            return pstmt.executeUpdate();
        }
    }

    // ======================== 과제 수정 =============================
    public int updateSubmission(Connection con, Long assignmentId, Long studentId, String newContent) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.update");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newContent);
            pstmt.setLong(2, assignmentId);
            pstmt.setLong(3, studentId);

            return pstmt.executeUpdate();
        }
    }

    // ===================== 과제 삭제 ======================
    public int deleteSubmission(Connection con, Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.delete");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, studentId);

            return pstmt.executeUpdate();
        }
    }

}