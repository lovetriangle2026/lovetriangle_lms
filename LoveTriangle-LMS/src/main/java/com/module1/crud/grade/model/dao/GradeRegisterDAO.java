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

    // 💡 생성자와 필드 제거 (상태를 가지지 않는 가벼운 DAO)
    public GradeRegisterDAO() {
    }

    // 과제 점수 미등록 학생 목록 조회
    public List<GradeRegisterDTO> findAssignmentRegisterTargets(Connection con, long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findAssignmentRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
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
        }
        return registerList;
    }

    // 과제 점수 등록
    public int registerAssignmentScore(Connection con, int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerAssignmentScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            // 쿼리문: SELECT ?, a.id, NULL, ? FROM ... WHERE a.course_id = ?
            pstmt.setInt(1, studentId); // 첫 번째 ?: student_id
            pstmt.setInt(2, score);     // 두 번째 ?: score
            pstmt.setInt(3, courseId);  // 세 번째 ?: course_id

            int result = pstmt.executeUpdate();
            return result;
        }
    }

    public List<GradeRegisterDTO> findMidtermRegisterTargets(Connection con, long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findMidtermRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
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
        }
        return registerList;
    }

    public int registerMidtermScore(Connection con, int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerMidtermScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, score);

            return pstmt.executeUpdate();
        }
    }

    public List<GradeRegisterDTO> findFinalRegisterTargets(Connection con, long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findFinalRegisterTargets");
        List<GradeRegisterDTO> registerList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
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
        }
        return registerList;
    }

    public int registerFinalScore(Connection con, int studentId, int courseId, int score) throws SQLException {
        String query = QueryUtil.getQuery("grade.registerFinalScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, score);

            return pstmt.executeUpdate();
        }
    }
}