package com.module1.crud.course.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.course.model.dto.SessionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public SessionDAO() {
    }

    public int insertAutoSession(Connection con, int courseId, String title,
                                 Timestamp startAt, Timestamp endAt, int week) throws SQLException {
        String query = QueryUtil.getQuery("insert auto session");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, courseId);
            pstmt.setString(2, title);
            pstmt.setTimestamp(3, startAt);
            pstmt.setTimestamp(4, endAt);
            pstmt.setInt(5, week);

            return pstmt.executeUpdate();
        }
    }

    public List<SessionDTO> findSessionsByCourse(Connection con, int courseId) throws SQLException {
        String query = QueryUtil.getQuery("find sessions by course");
        List<SessionDTO> sessionList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    SessionDTO session = new SessionDTO(
                            rset.getInt("id"),
                            rset.getInt("course_id"),
                            rset.getString("title"),
                            rset.getTimestamp("start_at"),
                            rset.getTimestamp("end_at"),
                            rset.getInt("week")
                    );
                    sessionList.add(session);
                }
            }
        }
        return sessionList;
    }

    public int updateSessionTitle(Connection con, int courseId, int week, String title) throws SQLException {
        String query = QueryUtil.getQuery("update session title");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, week);

            return pstmt.executeUpdate();
        }
    }
}
