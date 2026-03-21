package com.module1.crud.course.model.service;

import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.course.model.dao.SessionDAO;
import com.module1.crud.course.model.dto.SessionDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SessionService {

    private final SessionDAO sessionDAO;

    public SessionService() {
        this.sessionDAO = new SessionDAO();
    }

    public boolean updateSessionTitle(int courseId, int week, String title) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = sessionDAO.updateSessionTitle(con, courseId, week, title);
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException("주차별 강의 내용 등록 중 Error 발생!! 🚨🚨", e);
        }
    }

    public List<SessionDTO> findSessionsByCourse(int courseId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return sessionDAO.findSessionsByCourse(con, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("주차별 강의 내용 조회 중 Error 발생!! 🚨🚨", e);
        }
    }
}
