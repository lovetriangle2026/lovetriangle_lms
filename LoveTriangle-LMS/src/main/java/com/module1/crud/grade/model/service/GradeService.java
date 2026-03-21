package com.module1.crud.grade.model.service;

import com.module1.crud.grade.model.dao.GradeEditDAO;
import com.module1.crud.grade.model.dao.GradeRegisterDAO;
import com.module1.crud.grade.model.dao.GradeViewDAO;
import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;
import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GradeService {

    private final GradeViewDAO gradeViewDAO;
    private final GradeEditDAO gradeEditDAO;
    private final GradeRegisterDAO gradeRegisterDAO;
    private final Connection connection;

    public GradeService(Connection connection) {
        this.gradeViewDAO = new GradeViewDAO(connection);
        this.gradeEditDAO = new GradeEditDAO(connection);
        this.gradeRegisterDAO = new GradeRegisterDAO(connection);
        this.connection = connection;
    }

    public List<GradeViewDTO> findAllGrade(long studentId) {
        try {
            return gradeViewDAO.findGrade(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 전체 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeViewDTO> getStudentgrade(long professorId, String studentName) {
        try {
            return gradeViewDAO.findByStudentName(professorId, studentName);
        } catch (SQLException e) {
            throw new RuntimeException("성적 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeViewDTO> getSallgradeByprofessor(long professorId) {
        try {
            return gradeViewDAO.findAllGradeByProfessor(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeEditDTO> getEditableGradeList(long professorId) {
        try {
            return gradeEditDAO.findEditableGradeListByProfessor(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("성적 수정 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateMidtermScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try {
            return gradeEditDAO.updateMidtermScore(studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateFinalScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try {
            return gradeEditDAO.updateFinalScore(studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("기말고사 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int updateAssignmentScore(int studentId, int courseId, int newScore) {
        validateScore(newScore);

        try {
            return gradeEditDAO.updateAssignmentScore(studentId, courseId, newScore);
        } catch (SQLException e) {
            throw new RuntimeException("과제 점수 수정 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getAssignmentRegisterTargets(long professorId) {
        try {
            return gradeRegisterDAO.findAssignmentRegisterTargets(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("과제 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerAssignmentScore(int studentId, int courseId, int score) {
        validateScore(score);

        try {
            return gradeRegisterDAO.registerAssignmentScore(studentId, courseId, score);
        } catch (SQLException e) {
            throw new RuntimeException("과제 점수 등록 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getMidtermRegisterTargets(long professorId) {
        try {
            return gradeRegisterDAO.findMidtermRegisterTargets(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerMidtermScore(int studentId, int courseId, int score) {
        validateScore(score);

        try {
            return gradeRegisterDAO.registerMidtermScore(studentId, courseId, score);
        } catch (SQLException e) {
            throw new RuntimeException("중간고사 점수 등록 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public List<GradeRegisterDTO> getFinalRegisterTargets(long professorId) {
        try {
            return gradeRegisterDAO.findFinalRegisterTargets(professorId);
        } catch (SQLException e) {
            throw new RuntimeException("기말고사 등록 대상 조회 중 Error 발생!! 🚨🚨 " + e);
        }
    }

    public int registerFinalScore(int studentId, int courseId, int score) {
        validateScore(score);

        try {
            return gradeRegisterDAO.registerFinalScore(studentId, courseId, score);
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

