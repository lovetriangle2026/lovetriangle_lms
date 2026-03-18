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

    public List<AssignmentDTO> findMyAssignments(Connection connection) throws SQLException {

        String query = QueryUtil.getQuery("assignment.findAll");
        List<AssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {

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

        return assignmentList;

    }
}
