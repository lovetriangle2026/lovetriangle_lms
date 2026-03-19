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

    public List<AssignmentDTO> findMyAssignments() throws SQLException {

        String query = QueryUtil.getQuery("assignment.findAll");
        System.out.println("query = " + query);
        List<AssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)){
                  ResultSet rset = pstmt.executeQuery();
                  System.out.println("rset = " + rset);

            while (rset.next()) {
                AssignmentDTO assignment = new AssignmentDTO(
                        rset.getLong("id"),
                        rset.getLong("course_id"),
                        rset.getString("title"),
                        rset.getString("description"),
                        rset.getTimestamp("deadline")
                );
                System.out.println("assignment = " + assignment);
                assignmentList.add(assignment);
            }
        }

        return assignmentList;

    }
}
