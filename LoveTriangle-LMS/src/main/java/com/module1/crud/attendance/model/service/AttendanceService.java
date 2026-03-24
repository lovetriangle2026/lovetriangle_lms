package com.module1.crud.attendance.model.service;

import com.module1.crud.attendance.model.dao.AttendanceDAO;
import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.attendance.model.dto.SessionDTO;
import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가!

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO;
    // 💡 Service 필드에서 Connection 제거

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    /**
     * 전체 출결 조회
     */
    public List<AttendanceDTO> findAllAttendance() {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findAll(con);
        } catch (SQLException e) {
            throw new RuntimeException("출결 전체 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 교수 담당 강의 목록 조회
     */
    public List<ProfessorCourseDTO> findCoursesByProfessorId(int professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findCoursesByProfessorId(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 강의 목록 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 강의별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseId(int courseId, int professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByCourseIdAndProfessorId(con, courseId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 강의별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 강의 + 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseIdAndWeek(int courseId, int professorId, int week) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByCourseIdAndWeekAndProfessorId(con, courseId, professorId, week);
        } catch (SQLException e) {
            throw new RuntimeException("강의별 주차 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 강의 + 출결 상태별 조회
     */
    public List<AttendanceDTO> findAttendanceByCourseIdAndStatus(int courseId, int professorId, String status) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByCourseIdAndStatusAndProfessorId(con, courseId, professorId, status);
        } catch (SQLException e) {
            throw new RuntimeException("강의별 출결 유형 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 기존 주차별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByWeek(int week) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByWeek(con, week);
        } catch (SQLException e) {
            throw new RuntimeException("주차별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<AttendanceDTO> findAttendanceByWeek(int week, int professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByWeekAndProfessorId(con, week, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 주차별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 기존 출결 상태별 조회
     */
    public List<AttendanceDTO> findAttendanceByStatus(String status) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByStatus(con, status);
        } catch (SQLException e) {
            throw new RuntimeException("출결 상태별 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 학생별 출결 조회
     */
    public List<AttendanceDTO> findAttendanceByStudentId(int studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByStudentId(con, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("학생별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<ProfessorCourseDTO> findCoursesByStudentId(int studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findCoursesByStudentId(con, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("학생 수강 강의 목록 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<AttendanceDTO> findAttendanceByStudentIdAndCourseId(int studentId, int courseId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findByStudentIdAndCourseId(con, studentId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("학생 강의별 출결 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 교수가 학생 출결 수정
     * */
    public boolean updateAttendanceStatus(int attendanceId, String status) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.updateAttendanceStatus(con, attendanceId, status);
        } catch (SQLException e) {
            throw new RuntimeException("출결 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    /**
     * 학생 출석체크
     * */
    public List<SessionDTO> findAvailableSessionByStudentId(int studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findAvailableSessionByStudentId(con, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("출석 가능한 수업 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }


    public String checkAttendance(int studentId, SessionDTO session) {
        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false);

            try {
                AttendanceDTO attendance = attendanceDAO.findByStudentIdAndSessionId(con, studentId, session.getId());
                String status;
// ✅ 예외 처리: 정글 아키텍처(course_id=1) 15주차는 무조건 출석
                if (session.getCourseId() == 1 && session.getWeek() == 15) {
                    status = "PRESENT";
                } else {
                    status = calculateAttendanceStatus(session);
                }

                boolean isSuccess;
                if (attendance == null) {
                    isSuccess = attendanceDAO.insertAttendance(con, studentId, session.getId(), status);
                } else {
                    isSuccess = attendanceDAO.updateAttendanceCheck(con, attendance.getId(), status);
                }

                String resultMessage = "출석체크 실패";

                if (isSuccess) {
                    resultMessage = "출석체크 완료! (상태: " + status + ")";

                    if ("PRESENT".equals(status)) {
                        int streak = attendanceDAO.getAttendanceStreak(
                                con,
                                studentId,
                                session.getCourseId(),
                                session.getId()
                        );

                        if (streak >= 3) {
                            resultMessage += "\n🔥" + streak + "일 연속 출석!! 현재 " + 3 + "일 이상 연속 출석 중입니다! 🎉";
                        }
                    }
                }

                con.commit();
                return resultMessage;

            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("출석체크 트랜잭션 중 Error 발생!! 🚨🚨", e);
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("출석체크 DB 연결 중 Error 발생!! 🚨🚨", e);
        }
    }


    private String calculateAttendanceStatus(SessionDTO session) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startAt = session.getStartAt().toLocalDateTime();

        LocalDateTime presentStart = startAt.minusMinutes(10);
        LocalDateTime lateStart = startAt.plusMinutes(10);

        if (!now.isBefore(presentStart) && now.isBefore(lateStart)) {
            return "PRESENT";
        } else {
            return "LATE";
        }
    }


    public List<SessionDTO> findSessionsByCourseId(int courseId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return attendanceDAO.findSessionsByCourseId(con, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("주차 조회 중 오류 🚨 " + e);
        }
    }

    public boolean applyExcuseRequest(int studentId, int sessionId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            AttendanceDTO attendance =
                    attendanceDAO.findByStudentIdAndSessionId(con, studentId, sessionId);

            if (attendance == null) {
                return false;
            }

            if ("EXCUSED".equals(attendance.getAttendanceStatus())
                    || "EXCUSED_PENDING".equals(attendance.getAttendanceStatus())) {
                return false;
            }

            int result = attendanceDAO.updateToExcusedPending(con, studentId, sessionId);
            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException("공결 신청 중 오류 🚨 " + e);
        }
    }

    }
