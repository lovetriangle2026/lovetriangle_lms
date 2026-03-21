package com.module1.crud.grade.view;

import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;

import java.util.List;

public class ProfessorGradeOutputView {

    public void printError(String s) {
        System.out.println(s);
    }

    public void printGrades(List<GradeViewDTO> GradeList) {
        if(GradeList == null || GradeList.isEmpty()){
            System.out.println("조회 된 성적이 없습니다!!");
            return;
        }
        System.out.println("============강의 전체 조회 목록 결과==============");
        for(GradeViewDTO courseDTO : GradeList){
            System.out.println(courseDTO);
        }
    }

    public void printstudentGrades(List<GradeViewDTO> GradeList){
        for(GradeViewDTO gradeDTO : GradeList)
            System.out.println(gradeDTO);
    }

    public void printmessage(String s) {
        System.out.println(s);
    }


    public void printEditableGradeList(List<GradeEditDTO> gradeList) {
        System.out.println("=================================");
        System.out.println("        수정 가능한 성적 목록");
        System.out.println("=================================");

        for (int i = 0; i < gradeList.size(); i++) {
            GradeEditDTO grade = gradeList.get(i);
            System.out.println((i + 1) + ". 학생명 : " + grade.getStudentName()
                    + " / 과목명 : " + grade.getCourseTitle()
                    + " / 중간 : " + grade.getMidtermScore()
                    + " / 기말 : " + grade.getFinalScore()
                    + " / 과제 : " + grade.getAssignmentScore());
        }
    }

    public void printAssignmentRegisterTargets(List<GradeRegisterDTO> registerList) {
        System.out.println("=================================");
        System.out.println("      과제 평가 등록 대상 목록");
        System.out.println("=================================");

        for (int i = 0; i < registerList.size(); i++) {
            GradeRegisterDTO dto = registerList.get(i);
            System.out.println((i + 1) + ". 학생명 : " + dto.getStudentName()
                    + " / 과목명 : " + dto.getCourseTitle());
        }
    }
}
