package com.module1.crud.assignments.model.dao;

import com.module1.crud.assignments.model.dto.*;
import com.module1.crud.global.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssignmentDAO {

    // 💡 더 이상 Connection을 필드로 가지지 않습니다.
    public AssignmentDAO() {
    }

    // ========================================학생 파트 ===========================================
    // ======================== 과제 조회 =========================
    public List<StudentAssignmentDTO> findMyAssignments(Connection con, Long userId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.findMyAssignments");
        List<StudentAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    StudentAssignmentDTO assignment = new StudentAssignmentDTO(
                            rset.getLong("id"),
                            rset.getLong("course_id"),
                            rset.getString("course_title"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getTimestamp("deadline"),
                            rset.getString("submission_status"),
                            rset.getString("submission_content"),
                            rset.getTimestamp("submitted_at")
                    );

                    assignmentList.add(assignment);
                }
            }
        }

        return assignmentList;
    }

    public Map<Long, String> findMyCourses(Connection con, Long userId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.findMyCourses");
        Map<Long, String> courseMap = new LinkedHashMap<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    courseMap.put(
                            rset.getLong("course_id"),
                            rset.getString("course_title")
                    );
                }
            }
        }

        return courseMap;
    }

    // ============================ 과제 제출 ============================
    public boolean existsMyAssignment(Connection con, Long assignmentId, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("assignment.existsMyAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, studentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    // 제출할 때 마감일이 지났는지 확인하기 위한 코드
    public Timestamp findAssignmentDeadline(Connection con, Long assignmentId) throws SQLException {
        String query = "SELECT deadline FROM assignments WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getTimestamp("deadline");
                }
            }
        }

        return null;
    }


    // ====================================== 교수 파트 ========================================
    // ================= 과제 조회 ====================
    public Map<Long, String> findProfessorCourses(Connection con, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findProfessorCourses");
        Map<Long, String> courseMap = new LinkedHashMap<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    courseMap.put(
                            rset.getLong("course_id"),
                            rset.getString("course_title")
                    );
                }
            }
        }

        return courseMap;
    }

    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Connection con, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findAssignmentsByProfessor");
        List<ProfessorAssignmentDTO> assignmentList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    ProfessorAssignmentDTO dto = new ProfessorAssignmentDTO(
                            rset.getLong("assignment_id"),
                            rset.getLong("course_id"),
                            rset.getString("course_title"),
                            rset.getString("assignment_title"),
                            rset.getString("description"),
                            rset.getTimestamp("deadline")
                    );
                    assignmentList.add(dto);
                }
            }
        }

        return assignmentList;
    }

    public boolean existsProfessorAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.findSubmissionStatusByAssignment");
        List<ProfessorAssignmentSubmissionDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    ProfessorAssignmentSubmissionDTO dto = new ProfessorAssignmentSubmissionDTO(
                            rset.getLong("student_id"),
                            rset.getString("student_name"),
                            rset.getString("submission_status"),
                            rset.getString("content_title"),
                            rset.getTimestamp("submitted_at")
                    );
                    list.add(dto);
                }
            }
        }

        return list;
    }

    // ================== 과제 생성 ==================
    public boolean existsProfessorCourse(Connection con, Long courseId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsProfessorCourse");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsAssignmentByCourse(Connection con, Long courseId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.existsAssignmentByCourse");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int createAssignment(Connection con, Long courseId, String title, String description,
                                Timestamp deadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.create");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setTimestamp(4, deadline);

            return pstmt.executeUpdate();
        }
    }

    // ==================== 과제 수정 ==================
    public int updateProfessorAssignment(Connection con, Long assignmentId, Long professorId,
                                         String newTitle, String newDescription,
                                         java.sql.Timestamp newDeadline) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.update");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newDescription);
            pstmt.setTimestamp(3, newDeadline);
            pstmt.setLong(4, assignmentId);
            pstmt.setLong(5, professorId);

            return pstmt.executeUpdate();
        }
    }

    // ===================== 과제 삭제 ======================
    public int deleteProfessorAssignment(Connection con, Long assignmentId, Long professorId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.delete");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, professorId);

            return pstmt.executeUpdate();
        }
    }

    public int deleteSubmissionsByAssignment(Connection con, Long assignmentId) throws SQLException {
        String query = QueryUtil.getQuery("professorAssignment.deleteSubmissionsByAssignment");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            return pstmt.executeUpdate();
        }
    }

    // ================== [신규] 상호 평가 (Peer Review) 파트 ==================

    // 1. 태그 목록 조회
    public List<HeartTagDTO> findAllHeartTags(Connection con) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.findAllTags");
        List<HeartTagDTO> tags = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {
            while (rset.next()) {
                tags.add(new HeartTagDTO(
                        rset.getInt("id"),
                        rset.getString("category"),
                        rset.getString("tag_name"),
                        rset.getString("description")
                ));
            }
        }
        return tags;
    }

    // 2. 팀원 목록 조회 (본인 제외)
    public List<TeamMemberDTO> findTeamMembers(Connection con, Long studentId, Long assignmentId) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.findTeamMembers");
        List<TeamMemberDTO> members = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            pstmt.setLong(2, assignmentId);
            pstmt.setLong(3, studentId); // 본인 제외용

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    members.add(new TeamMemberDTO(
                            rset.getLong("id"),
                            rset.getString("name")
                    ));
                }
            }
        }
        return members;
    }

    // 3. 중복 리뷰 체크
    public boolean existsPeerReview(Connection con, Long assignmentId, Long reviewerId, Long revieweeId) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.checkDuplicate");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, reviewerId);
            pstmt.setLong(3, revieweeId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // 4. 리뷰 메인 데이터 삽입 (PK 반환)
    public Long insertPeerReview(Connection con, Long assignmentId, Long reviewerId, Long revieweeId) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.insertReview");

        // RETURN_GENERATED_KEYS 적용 완료
        try (PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, assignmentId);
            pstmt.setLong(2, reviewerId);
            pstmt.setLong(3, revieweeId);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return null;
    }

    // 5. 선택된 칭찬 태그 삽입
    public void insertPeerReviewTag(Connection con, Long reviewId, int tagId) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.insertReviewTag");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, reviewId);
            pstmt.setInt(2, tagId);
            pstmt.executeUpdate();
        }
    }

    // 내가 소속된 팀플 과제 목록 조회
    public List<StudentTeamAssignmentDTO> findMyTeamAssignments(Connection con, Long studentId) throws SQLException {
        String query = QueryUtil.getQuery("peerReview.findMyTeamAssignments");
        List<StudentTeamAssignmentDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    list.add(new StudentTeamAssignmentDTO(
                            rset.getLong("team_assignment_id"),
                            rset.getString("course_title"),
                            rset.getString("assignment_title"),
                            rset.getTimestamp("deadline")
                    ));
                }
            }
        }
        return list;
    }

}