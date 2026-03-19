package com.module1.crud.assignments.model.service;

import com.module1.crud.assignments.model.dao.AssignmentDAO;
import com.module1.crud.assignments.model.dao.AssignmentSubmissionDAO;
import com.module1.crud.assignments.model.dto.AssignmentDTO;
import com.module1.crud.assignments.model.dto.AssignmentSubmissionDTO;

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

    public List<AssignmentDTO> findMyAssignments(Long userId) {
        try {
            return AssignmentDAO.findMyAssignments(userId);
        } catch (SQLException e) {
            throw new RuntimeException("학생 과제 조회 중 오류 발생 🚨 " + e);
        }
    }
    public void createSubmission(AssignmentSubmissionDTO submissionDTO) {
        try {
            boolean exists = AssignmentSubmissionDAO.existsByAssignmentAndStudent(
                    submissionDTO.getAssignmentId(),
                    submissionDTO.getStudentId()
            );

            if (exists) {
                throw new RuntimeException("이미 제출한 과제입니다.");
            }

            int result = AssignmentSubmissionDAO.createSubmission(submissionDTO);

            if (result <= 0) {
                throw new RuntimeException("과제 제출에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 제출 중 오류 발생 🚨 " + e);
        }

    }
}
