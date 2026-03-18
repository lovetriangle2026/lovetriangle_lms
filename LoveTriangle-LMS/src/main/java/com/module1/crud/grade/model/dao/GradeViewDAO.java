package com.module1.crud.grade.model.dao;

public class GradeViewDAO {
    private final Connection connection;

    public GradeViewDAO(Connection connection){
        this.connection = connection;
    }


    public List<GradeViewDTO> findGrade() throws SQLException {
        // 동작시킬 쿼리문 준비
        String query = QueryUtil.getQuery("grade.findall");

        List<GradeViewDTO> gradeList = new ArrayList<>();

        // 쿼리문 동작
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                GradeViewDTO grade = new GradeViewDTO(
                        rset.getInt("student_id"),
                        rset.getString("student_name"),
                        rset.getInt("course_id"),
                        rset.getString("assignment_title"),
                        rset.getInt("midterm_score"),
                        rset.getInt("midterm_score"),
                        rset.getDouble("midterm_35"),
                        rset.getDouble("final_35"),
                        rset.getDouble("attendance_score"),
                        rset.getInt("assignment_score"),
                        rset.getDouble("total_score"),
                        rset.getString("grade")
                );
                gradeList.add(grade);

            }

        }
        return gradeList;
    }
}
