package com.module1.crud.grade.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRegisterDAO {

    private final Connection connection;

    public GradeRegisterDAO(Connection connection) {
        this.connection = connection;
    }

    // 과제 점수 미등록 학생 목록 조회
    public List<GradeRegisterDTO> findAssignmentRegisterTargets(long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findAssignmentRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeRegisterDTO dto = new GradeRegisterDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title")
                );
                registerList.add(dto);
            }
        }

        return registerList;
    }

    // 과제 점수 등록
    public int registerAssignmentScore(int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerAssignmentScore");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, score);
            pstmt.setInt(3, courseId);

            return pstmt.executeUpdate();
        }
    }
    public List<GradeRegisterDTO> findMidtermRegisterTargets(long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findMidtermRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeRegisterDTO dto = new GradeRegisterDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title")
                );
                registerList.add(dto);
            }
        }

        return registerList;
    }
    public int registerMidtermScore(int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerMidtermScore");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, score);

            return pstmt.executeUpdate();
        }
    }
    public List<GradeRegisterDTO> findFinalRegisterTargets(long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findFinalRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeRegisterDTO dto = new GradeRegisterDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title")
                );
                registerList.add(dto);
            }
        }

        return registerList;
    }
    public int registerFinalScore(int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerFinalScore");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, score);

            return pstmt.executeUpdate();
        }
    }
}