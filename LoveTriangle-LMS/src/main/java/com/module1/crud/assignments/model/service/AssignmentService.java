package com.module1.crud.assignments.model.service;

import com.module1.crud.assignments.model.dao.AssignmentDAO;
import com.module1.crud.assignments.model.dao.AssignmentSubmissionDAO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentSubmissionDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AssignmentService {

    private final AssignmentDAO assignmentDAO;
    private final AssignmentSubmissionDAO assignmentSubmissionDAO;
    private final Connection connection;

    public AssignmentService(Connection connection) {
        assignmentDAO = new AssignmentDAO(connection);
        assignmentSubmissionDAO = new AssignmentSubmissionDAO(connection);
        this.connection = connection;
    }
    // =================================== 학생 파트 =========================================
    // ============================ 과제 조회 ==========================
    public List<StudentAssignmentDTO> findMyAssignments(Long userId) {
        try {
            return assignmentDAO.findMyAssignments(userId);
        } catch (SQLException e) {
            throw new RuntimeException("학생 과제 조회 중 오류 발생 🚨 " + e);
        }
    }

    // ============================== 과제 제출 =========================
    public boolean canSubmitAssignment(Long assignmentId, Long studentId) {
        try {
            return assignmentDAO.existsMyAssignment(assignmentId, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean isAlreadySubmitted(Long assignmentId, Long studentId) {
        try {
            return assignmentSubmissionDAO.existsByAssignmentAndStudent(assignmentId, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("제출 여부 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public void createSubmission(StudentAssignmentSubmissionDTO submissionDTO) {
        try {
            int result = assignmentSubmissionDAO.createSubmission(submissionDTO);

            if (result <= 0) {
                throw new RuntimeException("과제 제출에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 제출 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ============================== 과제 수정 =============================
    public void updateSubmission(Long assignmentId, Long studentId, String newContent) {
        try {
            int result = assignmentSubmissionDAO.updateSubmission(assignmentId, studentId, newContent);

            if (result <= 0) {
                throw new RuntimeException("과제 수정에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 수정 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ============================== 과제 삭제 =============================
    public void deleteSubmission(Long assignmentId, Long studentId) {
        try {
            int result = assignmentSubmissionDAO.deleteSubmission(assignmentId, studentId);

            if (result <= 0) {
                throw new RuntimeException("과제 삭제에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("과제 삭제 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    // ======================================= 교수 파트 =====================================
    // ========================== 과제 조회 =========================
    public List<ProfessorAssignmentDTO> findAssignmentsByProfessor(Long professorId) {
        try {
            return assignmentDAO.findAssignmentsByProfessor(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 조회 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean existsProfessorAssignment(Long assignmentId, Long professorId) {
        try {
            return assignmentDAO.existsProfessorAssignment(assignmentId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public List<ProfessorAssignmentSubmissionDTO> findSubmissionStatusByAssignment(Long assignmentId, Long professorId) {
        try {
            return assignmentDAO.findSubmissionStatusByAssignment(assignmentId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("과제별 학생 제출 현황 조회 중 오류 발생 🚨 " + e.getMessage());
        }
    }


    // ======================== 과제 생성 ========================
    public boolean existsProfessorCourse(Long courseId, Long professorId) {
        try {
            return assignmentDAO.existsProfessorCourse(courseId, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("강의 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public boolean existsAssignmentByCourse(Long courseId) {
        try {
            return assignmentDAO.existsAssignmentByCourse(courseId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 중복 확인 중 오류 발생 🚨 " + e.getMessage());
        }
    }

    public void createAssignment(Long courseId, String title, String description,
                                 Timestamp deadline) {
        try {
            int result = assignmentDAO.createAssignment(courseId, title, description, deadline);

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
        try {
            int result = assignmentDAO.updateProfessorAssignment(
                    assignmentId, professorId, newTitle, newDescription, newDeadline
            );

            if (result <= 0) {
                throw new RuntimeException("과제 수정에 실패했습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("교수 과제 수정 중 오류 발생 🚨 " + e.getMessage());
        }
    }
}
