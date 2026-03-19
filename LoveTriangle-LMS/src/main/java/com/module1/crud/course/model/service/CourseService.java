package com.module1.crud.course.model.service;

import com.module1.crud.course.model.dao.CourseDAO;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.module1.crud.global.config.JDBCTemplate.close;
import static com.module1.crud.global.config.JDBCTemplate.getConnection;

public class CourseService {

    private final CourseDAO courseDAO;
    private final Connection connection;

    public CourseService(Connection connection) {
        this.courseDAO = new CourseDAO(connection);
        this.connection = connection;
    }

    public List<CourseDTO> findAllCourses() {

        try {
            return courseDAO.findall();
        } catch (SQLException e) {
            throw new RuntimeException("강의 전체 조회 중 Error 발생!! 🚨🚨" + e);
        }
    }

    public List<CourseDTO> findMyCourses(Long userId) throws SQLException {
        Connection con = JDBCTemplate.getConnection();

        List<CourseDTO> myCourses = courseDAO.findMyCourses(userId);

        con.close();
        return myCourses;
    }




}
