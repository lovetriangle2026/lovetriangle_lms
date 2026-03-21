package com.module1.crud.grade.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.grade.model.dto.GradeEditDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeEditDAO {

    // 💡 생성자와 필드 제거
    public GradeEditDAO() {
    }

    // 교수의 강의를 듣는 학생 목록 조회
    public List<GradeEditDTO> findEditableGradeListByProfessor(Connection con, long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.editListByProfessor");

        List<GradeEditDTO> gradeList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeEditDTO grade = new GradeEditDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        rset.getInt("midterm_score"),
                        rset.getInt("final_score"),
                        rset.getInt("assignment_score")
                );
                gradeList.add(grade);
            }
        }

        return gradeList;
    }

    // 중간고사 점수 수정
    public int updateMidtermScore(Connection con, int studentId, int courseId, int midtermScore) throws SQLException {
        String query = QueryUtil.getQuery("grade.updateMidtermScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, midtermScore);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);

            return pstmt.executeUpdate();
        }
    }

    // 기말고사 점수 수정
    public int updateFinalScore(Connection con, int studentId, int courseId, int finalScore) throws SQLException {
        String query = QueryUtil.getQuery("grade.updateFinalScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, finalScore);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);

            return pstmt.executeUpdate();
        }
    }

    // 과제 점수 수정
    public int updateAssignmentScore(Connection con, int studentId, int courseId, int assignmentScore) throws SQLException {
        String query = QueryUtil.getQuery("grade.updateAssignmentScore");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, assignmentScore);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);

            return pstmt.executeUpdate();
        }
    }
}