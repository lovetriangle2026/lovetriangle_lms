package com.module1.crud.course.model.dao;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public CourseDAO() {
    }

    public List<CourseDTO> findall(Connection con) throws SQLException {
        String query = QueryUtil.getQuery("find all courses");
        List<CourseDTO> courselist = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    CourseDTO course = new CourseDTO(
                            rset.getLong("id"),
                            rset.getString("course_code"),
                            rset.getInt("professor_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getString("semester")
                    );
                    courselist.add(course);
                }
            }
        }
        return courselist;
    }

    public List<CourseDTO> findMyCourses(Connection con, int userId) throws SQLException {
        String query = QueryUtil.getQuery("find my courses");
        List<CourseDTO> courselist = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    CourseDTO course = new CourseDTO(
                            rset.getLong("id"),
                            rset.getString("course_code"),
                            rset.getInt("professor_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getString("semester")
                    );
                    courselist.add(course);
                }
            }
        }
        return courselist;
    }

    public int insertCourse(Connection con, CourseDTO course) throws SQLException {
        String sql = QueryUtil.getQuery("insert course");

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourse_code());
            pstmt.setInt(2, course.getProfessor_id());
            pstmt.setString(3, course.getTitle());
            pstmt.setString(4, course.getDescription());
            pstmt.setString(5, course.getSemester());

            return pstmt.executeUpdate();
        }
    }

    public boolean isAlreadyEnrolled(Connection con, int userId, int courseId) throws SQLException {
        String query = QueryUtil.getQuery("course.checkEnrollment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int insertEnrollment(Connection con, int userId, int courseId) throws SQLException {
        String query = QueryUtil.getQuery("course.insertEnrollment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);

            return pstmt.executeUpdate();
        }
    }

    public List<CourseDTO> findProfessorCourses(Connection con, int professorId) throws SQLException {
        String query = QueryUtil.getQuery("find professor courses");
        List<CourseDTO> courselist = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    CourseDTO course = new CourseDTO(
                            rset.getLong("id"),
                            rset.getString("course_code"),
                            rset.getInt("professor_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getString("semester")
                    );
                    courselist.add(course);
                }
            }
        }
        return courselist;
    }
}