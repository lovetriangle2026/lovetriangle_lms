package com.module1.crud.course.model.dao;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.Connection;
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
        //여ㅣ기서 뭘 쓰다가 try로 감싸주게되는지

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
    public List<CourseDTO> findMyCourses(Long userId) {
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

}
