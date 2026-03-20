package com.module1.crud.course.model.dao;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CourseDTO> findall() throws SQLException {

        String query = QueryUtil.getQuery("find all courses");
        List<CourseDTO> courselist = new ArrayList<>();

        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(query)) {
            java.sql.ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
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
        return courselist;

    }
    public List<CourseDTO> findMyCourses(int userId) {
        String query = com.module1.crud.global.utils.QueryUtil.getQuery("find my courses");
        List<CourseDTO> courselist = new java.util.ArrayList<>();

        try(java.sql.PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, userId);

            try (java.sql.ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    CourseDTO course = new CourseDTO(
                            rset.getLong("id"),
                            rset.getString("course_code"),
                            rset.getInt("professor_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getString("semester"));

                    courselist.add(course);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }return courselist;

    }
    public int insertCourse(CourseDTO course){
        Connection con = null;
        PreparedStatement pstmt = null;
        int result = 0; // 등록 성공 여부

        String sql = QueryUtil.getQuery("insert course");

        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, course.getCourse_code());
            pstmt.setInt(2, course.getProfessor_id());
            pstmt.setString(3, course.getTitle());
            pstmt.setString(4, course.getDescription());
            pstmt.setString(5,course.getSemester());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;


    }




}
