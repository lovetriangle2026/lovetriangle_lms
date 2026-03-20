package com.module1.crud.grade.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.module1.crud.global.config.JDBCTemplate.close;

public class GradeViewDAO {
    private final Connection connection;

    public GradeViewDAO(Connection connection) {
        this.connection = connection;
    }


    public List<GradeViewDTO> findGrade(long studentId) throws SQLException {
        // 동작시킬 쿼리문 준비
        String query = QueryUtil.getQuery("grade.findall");


        List<GradeViewDTO> gradeList = new ArrayList<>();

        // 쿼리문 동작
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        rset.getInt("midterm_score"),
                        rset.getInt("final_score"),
                        rset.getDouble("midterm_35"),
                        rset.getDouble("final_35"),
                        rset.getDouble("attendance_score"),
                        rset.getInt("assignment_score"),
                        rset.getDouble("total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);

            }

        }
        return gradeList;
    }

    public List<GradeViewDTO> findByStudentName(long professorId, String studentName) throws SQLException {
        // 동작시킬 쿼리문 준비
        String query = QueryUtil.getQuery("grade.findById");


        List<GradeViewDTO> gradeList = new ArrayList<>();

        // 쿼리문 동작
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, professorId);
            pstmt.setString(2, studentName);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        rset.getInt("midterm_score"),
                        rset.getInt("final_score"),
                        rset.getDouble("midterm_35"),
                        rset.getDouble("final_35"),
                        rset.getDouble("attendance_score"),
                        rset.getInt("assignment_score"),
                        rset.getDouble("total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);

            }

        }
        return gradeList;
    }

    public List<GradeViewDTO> findAllGradeByProfessor(long professorId) throws SQLException {
        // 동작시킬 쿼리문 준비
        String query = QueryUtil.getQuery("grade.findAllByProfessor");

        List<GradeViewDTO> gradeList = new ArrayList<>();

        // 쿼리문 동작
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        rset.getInt("midterm_score"),
                        rset.getInt("final_score"),
                        rset.getDouble("midterm_35"),
                        rset.getDouble("final_35"),
                        rset.getDouble("attendance_score"),
                        rset.getInt("assignment_score"),
                        rset.getDouble("total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);
            }
        }

        return gradeList;
    }
}
