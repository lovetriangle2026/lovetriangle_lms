package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.AssignmentDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    private final Connection connection;

    public AssignmentDAO(Connection connection) {

        this.connection = connection;
    }

    // ======================== 과제 조회 =========================
    public List<AssignmentDTO> findMyAssignments(Long userId) throws SQLException {

        String query = QueryUtil.getQuery("assignment.findAll");
        List<AssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {
                    AssignmentDTO assignment = new AssignmentDTO(
                            rset.getLong("id"),
                            rset.getLong("course_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getTimestamp("deadline")
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

}


