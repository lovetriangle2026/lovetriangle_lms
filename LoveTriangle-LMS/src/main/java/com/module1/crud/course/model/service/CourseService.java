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

    public List<CourseDTO> findMyCourses(int userId) throws SQLException {
        Connection con = JDBCTemplate.getConnection();

        List<CourseDTO> myCourses = courseDAO.findMyCourses(userId);

        con.close();
        return myCourses;
    }

    public int insertCourse(CourseDTO course){
        //강의코드 자동생성 ...
        // c - 랜덤숫자
        String autoCode = "c-" + (int)(Math.random() * 900) + 100;
        course.setCourse_code(autoCode);

        int result = courseDAO.insertCourse(course);

        return result;
    }

    public boolean enrollCourse(int userId, int courseId) {
        if (courseDAO.isAlreadyEnrolled(userId, courseId)) {
            return false;
        }

        int result = courseDAO.insertEnrollment(userId, courseId);
        return result > 0;
    }

    public List<CourseDTO> findProfessorCourses(int professorId) {
        try {
            return courseDAO.findProfessorCourses(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("담당 강의 조회 중 오류 발생!! 🚨🚨 " + e);
        }
    }


}
