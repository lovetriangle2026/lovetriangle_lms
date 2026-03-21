package com.module1.crud.course.model.service;

import com.module1.crud.course.model.dao.CourseDAO;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO;

    // 💡 생성자에서 Connection 인자 제거 및 DAO 기본 생성
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
        // 💡 수동으로 con.close() 하던 것을 try-with-resources 로 안전하게 변경!
        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.findMyCourses(con, userId);
        } catch (SQLException e) {
            throw new RuntimeException("내 강의 조회 중 Error 발생!! 🚨🚨", e);
        }
    }

    public int insertCourse(CourseDTO course) {
        // 강의코드 자동생성
        String autoCode = "c-" + (int)(Math.random() * 900) + 100;
        course.setCourse_code(autoCode);

        try (Connection con = JDBCTemplate.getConnection()) {
            return courseDAO.insertCourse(con, course);
        } catch (SQLException e) {
            throw new RuntimeException("강의 등록 중 Error 발생!! 🚨🚨", e);
        }
    }

    public boolean enrollCourse(int userId, int courseId) {
        // 💡 조회 후 등록(Check-then-Act) 패턴이므로 트랜잭션으로 안전하게 묶습니다!
        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false); // 🔒 트랜잭션 시작

            try {
                // 1. 이미 수강 중인지 확인
                if (courseDAO.isAlreadyEnrolled(con, userId, courseId)) {
                    return false; // 변경된 게 없으므로 롤백 없이 그냥 리턴
                }

                // 2. 수강 신청 진행
                int result = courseDAO.insertEnrollment(con, userId, courseId);

                if (result > 0) {
                    con.commit(); // 🔓 성공 시 확정
                    return true;
                } else {
                    con.rollback(); // 실패 시 원상복구
                    return false;
                }

            } catch (SQLException e) {
                con.rollback(); // 🚨 에러 시 원상복구
                throw new RuntimeException("수강 신청 트랜잭션 중 Error 발생!! 🚨🚨", e);
            } finally {
                con.setAutoCommit(true); // 자동 커밋 복구
            }

        } catch (SQLException e) {
            throw new RuntimeException("수강 신청 DB 연결 중 Error 발생!! 🚨🚨", e);
        }
    }
}