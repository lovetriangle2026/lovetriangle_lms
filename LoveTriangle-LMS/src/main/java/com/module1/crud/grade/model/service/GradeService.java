package com.module1.crud.grade.model.service;

import com.module1.crud.grade.model.dao.GradeEditDAO;
import com.module1.crud.grade.model.dao.GradeRegisterDAO;
import com.module1.crud.grade.model.dao.GradeViewDAO;
import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;
import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GradeService {

    private final GradeViewDAO gradeViewDAO;
    private final GradeEditDAO gradeEditDAO;
    private final GradeRegisterDAO gradeRegisterDAO;

    // 💡 생성자 인자 제거 및 DAO 기본 생성
    public GradeService() {
        this.gradeViewDAO = new GradeViewDAO();
        this.gradeEditDAO = new GradeEditDAO();
        this.gradeRegisterDAO = new GradeRegisterDAO();
    }

    public List<GradeViewDTO> findAllGrade(long studentId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeViewDAO.findGrade(con, studentId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 전체 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeViewDTO> getStudentgrade(long professorId, String studentName) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeViewDAO.findByStudentName(con, professorId, studentName);
        } catch (SQLException e) {
            throw new RuntimeException("성적 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeViewDTO> getSallgradeByprofessor(long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeViewDAO.findAllGradeByProfessor(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeEditDTO> getEditableGradeList(long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeEditDAO.findEditableGradeListByProfessor(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 수정 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateMidtermScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeEditDAO.updateMidtermScore(con, studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateFinalScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeEditDAO.updateFinalScore(con, studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("기말고사 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateAssignmentScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeEditDAO.updateAssignmentScore(con, studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("과제 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getAssignmentRegisterTargets(long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.findAssignmentRegisterTargets(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerAssignmentScore(int studentId, int courseId, int score) {
        validateScore(score);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.registerAssignmentScore(con, studentId, courseId, score);
        } catch (SQLException e) {
            throw new RuntimeException("과제 점수 등록 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getMidtermRegisterTargets(long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.findMidtermRegisterTargets(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerMidtermScore(int studentId, int courseId, int score) {
        validateScore(score);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.registerMidtermScore(con, studentId, courseId, score);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 점수 등록 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getFinalRegisterTargets(long professorId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.findFinalRegisterTargets(con, professorId);
        } catch (SQLException e) {
            throw new RuntimeException("기말고사 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerFinalScore(int studentId, int courseId, int score) {
        validateScore(score);

        try (Connection con = JDBCTemplate.getConnection()) {
            return gradeRegisterDAO.registerFinalScore(con, studentId, courseId, score);
        } catch (SQLException e) {
            throw new RuntimeException("기말고사 점수 등록 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    private void validateScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("점수는 0 이상 100 이하만 입력 가능합니다.");
        }
    }
}