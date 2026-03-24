package com.module1.crud.grade.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeViewDAO {

    // 💡 생성자와 필드 제거
    public GradeViewDAO() {
    }

    private Integer getNullableInt(ResultSet rset, String columnLabel) throws SQLException {
        Number value = (Number) rset.getObject(columnLabel);
        return value == null ? null : value.intValue();
    }

    private Double getNullableDouble(ResultSet rset, String columnLabel) throws SQLException {
        Number value = (Number) rset.getObject(columnLabel);
        return value == null ? null : value.doubleValue();
    }

    public List<GradeViewDTO> findGrade(Connection con, long studentId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findall");
        List<GradeViewDTO> gradeList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        getNullableInt(rset, "midterm_score"),
                        getNullableInt(rset, "final_score"),
                        getNullableDouble(rset, "midterm_35"),
                        getNullableDouble(rset, "final_35"),
                        getNullableDouble(rset, "attendance_score"),
                        getNullableInt(rset, "assignment_score"),
                        getNullableDouble(rset, "total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);
            }
        }

        return gradeList;
    }

    public List<GradeViewDTO> findByStudentName(Connection con, long professorId, String studentName) throws SQLException {
        String query = QueryUtil.getQuery("grade.findById");
        List<GradeViewDTO> gradeList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);
            pstmt.setString(2, studentName);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        getNullableInt(rset, "midterm_score"),
                        getNullableInt(rset, "final_score"),
                        getNullableDouble(rset, "midterm_35"),
                        getNullableDouble(rset, "final_35"),
                        getNullableDouble(rset, "attendance_score"),
                        getNullableInt(rset, "assignment_score"),
                        getNullableDouble(rset, "total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);
            }
        }

        return gradeList;
    }

    public List<GradeViewDTO> findAllGradeByProfessor(Connection con, long professorId) throws SQLException {
        String query = QueryUtil.getQuery("grade.findAllByProfessor");
        List<GradeViewDTO> gradeList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("course_title"),
                        getNullableInt(rset, "midterm_score"),
                        getNullableInt(rset, "final_score"),
                        getNullableDouble(rset, "midterm_35"),
                        getNullableDouble(rset, "final_35"),
                        getNullableDouble(rset, "attendance_score"),
                        getNullableInt(rset, "assignment_score"),
                        getNullableDouble(rset, "total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);
            }
        }

        return gradeList;
    }
}