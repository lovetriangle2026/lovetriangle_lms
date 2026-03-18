package com.module1.crud.grade.model.service;

public class GradeService {
    private final GradeViewDAO GradeViewDAO;
    private final Connection connection;

    public GradeService(Connection connection) {
        this.GradeViewDAO = new GradeViewDAO(connection);
        this.connection = connection;
    }

    public List<GradeViewDTO> findAllGrade() {

        try {
            return GradeViewDAO.findGrade();
        } catch (SQLException e) {
            throw new RuntimeException("성적 전체 조회 중 Error 발생!! 🚨🚨" + e);
        }
    }
}
