package com.module1.crud.assignments.model.service;

import com.module1.crud.assignments.model.dao.AssignmentDAO;
import com.module1.crud.assignments.model.dao.AssignmentSubmissionDAO;
import com.module1.crud.assignments.model.dto.AssignmentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AssignmentService {

    private final AssignmentDAO AssignmentDAO;
    private final AssignmentSubmissionDAO AssignmentSubmissionDAO;
    private final Connection connection;

    public AssignmentService(Connection connection) {
        AssignmentDAO = new AssignmentDAO(connection);
        AssignmentSubmissionDAO = new AssignmentSubmissionDAO(connection);
        this.connection = connection;
    }

    public List<AssignmentDTO> findMyAssignments() {
        try {
            return AssignmentDAO.findMyAssignments(connection);
        } catch (SQLException e) {
            throw new RuntimeException("학생 과제 조회 중 오류 발생 🚨 " + e);
        }
    }
}
