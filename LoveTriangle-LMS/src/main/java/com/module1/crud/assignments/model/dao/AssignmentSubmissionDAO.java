package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.AssignmentSubmissionDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentSubmissionDAO {

    private final Connection connection;

    public AssignmentSubmissionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean existsByAssignmentAndStudent(Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.existsByAssignmentAndStudent");

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

    public int createSubmission(AssignmentSubmissionDTO submissionDTO) throws SQLException {
        String query = QueryUtil.getQuery("assignmentSubmission.create");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, submissionDTO.getAssignmentId());
            pstmt.setLong(2, submissionDTO.getStudentId());
            pstmt.setString(3, submissionDTO.getContent());

            return pstmt.executeUpdate();
        }
    }
}