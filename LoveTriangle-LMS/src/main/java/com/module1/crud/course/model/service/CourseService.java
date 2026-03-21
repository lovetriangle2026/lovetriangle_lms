package com.module1.crud.course.model.service;

import com.module1.crud.course.model.dao.CourseDAO;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.course.model.dao.SessionDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class CourseService {

    private final CourseDAO courseDAO;
    private final SessionDAO sessionDAO;

    public CourseService() {
        this.courseDAO = new CourseDAO();
        this.sessionDAO = new SessionDAO();
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

    public boolean insertCourse(CourseDTO course) {
        String autoCode = "c-" + ((int)(Math.random() * 900) + 100);
        course.setCourse_code(autoCode);

        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false);

            try {
                int courseId = courseDAO.insertCourse(con, course);

                if (courseId <= 0) {
                    con.rollback();
                    return false;
                }

                LocalDate firstDate = LocalDate.of(2026, 3, 6);

                for (int week = 1; week <= 15; week++) {
                    LocalDate currentDate = firstDate.plusWeeks(week - 1);

                    LocalDateTime startDateTime = currentDate.atTime(9, 0);
                    LocalDateTime endDateTime = currentDate.atTime(12, 0);

                    Timestamp startAt = Timestamp.valueOf(startDateTime);
                    Timestamp endAt = Timestamp.valueOf(endDateTime);

                    int result = sessionDAO.insertAutoSession(
                            con,
                            courseId,
                            week + "주차",
                            startAt,
                            endAt,
                            week
                    );

                    if (result <= 0) {
                        con.rollback();
                        return false;
                    }
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeException("강의 등록 및 session 자동 생성 중 Error 발생!! 🚨🚨", e);
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("강의 등록 DB 연결 중 Error 발생!! 🚨🚨", e);
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