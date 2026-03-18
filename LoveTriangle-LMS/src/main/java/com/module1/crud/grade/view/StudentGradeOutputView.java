package com.module1.crud.grade.view;

import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.util.List;

public class StudentGradeOutputView {
    public void printMessage(String s) {
    }

    public void printError(String s) {
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
}
