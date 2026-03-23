package com.module1.crud.course.model.dao;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.CourseStudentStatsDTO;
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
        String query = QueryUtil.getQuery("insert course");

        try (PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getCourse_code());
            pstmt.setInt(2, course.getProfessor_id());
            pstmt.setString(3, course.getTitle());
            pstmt.setString(4, course.getDescription());
            pstmt.setString(5, course.getSemester());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rset = pstmt.getGeneratedKeys()) {
                    if (rset.next()) {
                        return rset.getInt(1);   // 생성된 course id 반환
                    }
                }
            }
        }
        return 0;
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

    // 수강생 동료 평가 통계 조회
    public List<CourseStudentStatsDTO> findStudentStatsByCourse(Connection con, int courseId) throws SQLException {
        List<CourseStudentStatsDTO> statsList = new ArrayList<>();
        String studentQuery = QueryUtil.getQuery("course.findEnrolledStudents");
        String heartQuery = QueryUtil.getQuery("course.getStudentTotalHearts");
        String tagQuery = QueryUtil.getQuery("course.getStudentTop3Tags");

        try (PreparedStatement pstmt = con.prepareStatement(studentQuery)) {
            pstmt.setInt(1, courseId);
            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    int studentId = rset.getInt("id");
                    String name = rset.getString("name");
                    int isHeartPublic = rset.getInt("is_heart_public");

                    int totalHearts = 0;
                    List<String> top3Tags = new ArrayList<>();

                    // 1. 하트 개수 세기
                    try (PreparedStatement heartPstmt = con.prepareStatement(heartQuery)) {
                        heartPstmt.setInt(1, studentId);
                        try (ResultSet heartRset = heartPstmt.executeQuery()) {
                            if (heartRset.next()) {
                                totalHearts = heartRset.getInt("total_hearts");
                            }
                        }
                    }

                    // 2. 상위 태그 3개 가져오기
                    try (PreparedStatement tagPstmt = con.prepareStatement(tagQuery)) {
                        tagPstmt.setInt(1, studentId);
                        try (ResultSet tagRset = tagPstmt.executeQuery()) {
                            while (tagRset.next()) {
                                top3Tags.add(tagRset.getString("tag_name") + " (" + tagRset.getInt("tag_count") + "회)");
                            }
                        }
                    }

                    statsList.add(new CourseStudentStatsDTO(studentId, name, isHeartPublic, totalHearts, top3Tags));
                }
            }
        }
        return statsList;
    }
}