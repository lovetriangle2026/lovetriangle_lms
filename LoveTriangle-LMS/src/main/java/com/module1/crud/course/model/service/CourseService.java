package com.module1.crud.course.model.service;

import com.module1.crud.course.model.dao.CourseDAO;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService() {
        this.courseDAO = new CourseDAO();
    }

    public List<CourseDTO> findAllCourses() {
        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.findall(con);
        } catch (SQLException e) {
            throw new RuntimeException("강의 전체 조회 중 Error 발생!! 🚨🚨", e);
        }
    }

    public List<CourseDTO> findMyCourses(int userId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.findMyCourses(con, userId);
        } catch (SQLException e) {
            throw new RuntimeException("내 강의 조회 중 Error 발생!! 🚨🚨", e);
        }
    }

    public int insertCourse(CourseDTO course) {
        String autoCode = "c-" + (int)(Math.random() * 900) + 100;
        course.setCourse_code(autoCode);

        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.insertCourse(con, course);
        } catch (SQLException e) {
            throw new RuntimeException("강의 등록 중 Error 발생!! 🚨🚨", e);
        }
    }

    public boolean enrollCourse(int userId, int courseId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false);

            try {
                if (courseDAO.isAlreadyEnrolled(con, userId, courseId)) {
                    return false;
                }

                int result = courseDAO.insertEnrollment(con, userId, courseId);

                if (result > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }

            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeException("수강 신청 트랜잭션 중 Error 발생!! 🚨🚨", e);
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("수강 신청 DB 연결 중 Error 발생!! 🚨🚨", e);
        }
    }

    // ✅ 이거 유지해야 함 (핵심 기능)
    public List<CourseDTO> findProfessorCourses(int professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.findProfessorCourses(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("담당 강의 조회 중 오류 발생!! 🚨🚨", e);
        }
    }
}