package com.module1.crud.assignments.model.service;

import com.module1.crud.assignments.model.dao.AssignmentDAO;
import com.module1.crud.assignments.model.dao.AssignmentSubmissionDAO;
import com.module1.crud.assignments.model.dto.*;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AssignmentService {

    private final AssignmentDAO assignmentDAO;
    private final AssignmentSubmissionDAO assignmentSubmissionDAO;

    // 💡 생성자에서 커넥션 인자를 완전히 없애고, DAO를 기본 생성자로 세팅합니다.
    public AssignmentService() {
        this.assignmentDAO = new AssignmentDAO();
        this.assignmentSubmissionDAO = new AssignmentSubmissionDAO();
    }

    // =================================== 학생 파트 =========================================
    // ============================ 과제 조회 ==========================
    public List<StudentAssignmentDTO> findMyAssignments(Long userId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.findMyAssignments(con, userId);
        } catch (SQLException e) {
            throw new RuntimeException("학생 과제 조회 중 오류 발생 🚨 " + e);
        }
    }

    // ============================== 과제 제출 =========================
    public boolean canSubmitAssignment(Long assignmentId, Long studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.existsMyAssignment(con, assignmentId, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean isAlreadySubmitted(Long assignmentId, Long studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentSubmissionDAO.existsByAssignmentAndStudent(con, assignmentId, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("제출 여부 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean isAssignmentDeadlinePassed(Long assignmentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            Timestamp deadline = assignmentDAO.findAssignmentDeadline(con, assignmentId);

            if (deadline == null) {
                throw new RuntimeException("과제 마감일 정보를 찾을 수 없습니다.");
            }

            return deadline.before(new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException("과제 마감일 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public void createSubmission(StudentAssignmentSubmissionDTO submissionDTO) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = assignmentSubmissionDAO.createSubmission(con, submissionDTO);

            if (result <= 0) {
                throw new RuntimeException("과제 제출에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 제출 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ============================== 과제 수정 =============================
    public void updateSubmission(Long assignmentId, Long studentId, String newContent) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = assignmentSubmissionDAO.updateSubmission(con, assignmentId, studentId, newContent);

            if (result <= 0) {
                throw new RuntimeException("과제 수정에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 수정 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ============================== 과제 삭제 =============================
    public void deleteSubmission(Long assignmentId, Long studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = assignmentSubmissionDAO.deleteSubmission(con, assignmentId, studentId);

            if (result <= 0) {
                throw new RuntimeException("과제 삭제에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 삭제 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ======================================= 교수 파트 =====================================
    // ========================== 과제 조회 =========================
    public Map<Long, String> findProfessorCourses(Long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.findProfessorCourses(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 강의 조회 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.findAssignmentsByProfessor(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 조회 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean existsProfessorAssignment(Long assignmentId, Long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.existsProfessorAssignment(con, assignmentId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Long assignmentId, Long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.findSubmissionStatusByAssignment(con, assignmentId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("과제별 학생 제출 현황 조회 중 오류 발생 🚨 " + e.getMessage());
        }
    }


    // ======================== 과제 생성 ========================
    public boolean existsProfessorCourse(Long courseId, Long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.existsProfessorCourse(con, courseId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("강의 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean existsAssignmentByCourse(Long courseId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return assignmentDAO.existsAssignmentByCourse(con, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 중복 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public void createAssignment(Long courseId, String title, String description,
                                 Timestamp deadline) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = assignmentDAO.createAssignment(con, courseId, title, description, deadline);

            if (result <= 0) {
                throw new RuntimeException("과제 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("과제 등록 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ======================= 과제 수정 =====================
    public void updateProfessorAssignment(Long assignmentId, Long professorId,
                                          String newTitle, String newDescription,
                                          java.sql.Timestamp newDeadline) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int result = assignmentDAO.updateProfessorAssignment(con, assignmentId, professorId, newTitle, newDescription, newDeadline);

            if (result <= 0) {
                throw new RuntimeException("과제 수정에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 수정 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ===================== 과제 삭제 (트랜잭션 관리!) =====================
    public void deleteProfessorAssignment(Long assignmentId, Long professorId) {
        // 💡 다중 쿼리이므로 Service가 커밋/롤백을 통제합니다!
        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false); // 트랜잭션 시작

            try {
                boolean exists = assignmentDAO.existsProfessorAssignment(con, assignmentId, professorId);
                if (!exists) {
                    throw new RuntimeException("본인이 생성한 과제가 아닙니다.");
                }

                assignmentDAO.deleteSubmissionsByAssignment(con, assignmentId);

                int result = assignmentDAO.deleteProfessorAssignment(con, assignmentId, professorId);

                if (result <= 0) {
                    throw new RuntimeException("과제 삭제에 실패했습니다.");
                }

                con.commit(); // 트랜잭션 성공

            } catch (Exception e) {
                try {
                    con.rollback(); // 실패 시 롤백
                } catch (SQLException rollbackException) {
                    throw new RuntimeException("과제 삭제 롤백 중 오류 발생 🚨 " + rollbackException.getMessage());
                }
                throw new RuntimeException("교수 과제 삭제 중 오류 발생 🚨 " + e.getMessage());

            } finally {
                try {
                    con.setAutoCommit(true); // 자동 커밋 모드 복구 (필수)
                } catch (SQLException e) {
                    throw new RuntimeException("오토커밋 복구 중 오류 발생 🚨 " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 중 오류 발생 🚨", e);
        }
    }

    public SubmissionRankResultDTO createSubmission(StudentAssignmentSubmissionDTO submissionDTO, boolean late) {
        try (Connection con = JDBCTemplate.getConnection()) {

            int result = assignmentSubmissionDAO.createSubmission(con, submissionDTO);

            if (result <= 0) {
                throw new RuntimeException("과제 제출에 실패했습니다. 🚨");
            }

            Timestamp submittedAt = assignmentSubmissionDAO.findSubmittedAtByAssignmentAndStudent(
                    con,
                    submissionDTO.getAssignmentId(),
                    submissionDTO.getStudentId()
            );

            if (submittedAt == null) {
                throw new RuntimeException("제출 시간 조회에 실패했습니다. 🚨");
            }

            int earlierCount = assignmentSubmissionDAO.countEarlierSubmissions(
                    con,
                    submissionDTO.getAssignmentId(),
                    submittedAt,
                    submissionDTO.getStudentId()
            );

            int rank = earlierCount + 1;

            int totalStudents = assignmentSubmissionDAO.countTotalStudentsByAssignment(
                    con,
                    submissionDTO.getAssignmentId()
            );

            int topTenCut = (int) Math.ceil(totalStudents * 0.1);

            boolean first = rank == 1;
            boolean topTenPercent = !first && rank <= topTenCut;

            return new SubmissionRankResultDTO(rank, totalStudents, first, topTenPercent, late);

        } catch (SQLException e) {
            throw new RuntimeException("과제 제출 중 오류 발생 🚨 " + e.getMessage());
        }
    }
}