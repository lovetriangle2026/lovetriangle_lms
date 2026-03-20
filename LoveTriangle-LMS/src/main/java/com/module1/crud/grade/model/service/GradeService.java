package com.module1.crud.grade.model.service;

import com.module1.crud.grade.model.dao.GradeViewDAO;
import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GradeService {
    private final GradeViewDAO GradeViewDAO;
    private final Connection connection;

    public GradeService(Connection connection) {
        this.GradeViewDAO = new GradeViewDAO(connection);
        this.connection = connection;
    }

    public List<GradeViewDTO> findAllGrade(long studentId) {

        try {
            return GradeViewDAO.findGrade(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 전체 조회 중 Error 발생!! 🚨🚨" + e);
        }
    }

    public List<GradeViewDTO> getStudentgrade(long professorId, String studentName) {

        try {
            return GradeViewDAO.findByStudentName(professorId,studentName);
        } catch (SQLException e) {
            throw new RuntimeException("성적 전체 조회 중 Error 발생!! 🚨🚨" + e);
        }
    }
}
