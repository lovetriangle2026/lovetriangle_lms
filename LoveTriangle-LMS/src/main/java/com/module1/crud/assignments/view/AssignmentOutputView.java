package com.module1.crud.assignments.view;

import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;

import java.util.List;
import java.util.Map;

public class AssignmentOutputView {
    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printError(String s) {
        System.out.println("🚨 " + s);
    }

    public void printAssignments(List<StudentAssignmentDTO> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("조회된 과제가 없습니다.");
            return;
        }

        System.out.println("=============== 수강 과제 조회 ===============");
        for (StudentAssignmentDTO dto : assignmentList) {
            System.out.println("강의명    : " + dto.getCourseTitle());
            System.out.println("과제명    : " + dto.getTitle());
            System.out.println("마감일    : " + dto.getDeadline());
            System.out.println("제출상태  : " + dto.getSubmissionStatus());
            System.out.println("====================================================");
        }
    }

    public void printStudentCourseMenu(List<String> courseTitles) {
        if (courseTitles == null || courseTitles.isEmpty()) {
            System.out.println("수강 중인 강의가 없습니다.");
            return;
        }

        System.out.println("강의를 선택해주세요.");
        for (int i = 0; i < courseTitles.size(); i++) {
            System.out.println((i + 1) + ". " + courseTitles.get(i));
        }
        System.out.println("0. 이전으로 돌아가기");
    }

    public void printStudentAssignmentMenu(String courseTitle, List<StudentAssignmentDTO> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("선택한 강의의 과제가 없습니다.");
            return;
        }

        System.out.println("[" + courseTitle + "]의 과제를 선택해주세요.");
        for (int i = 0; i < assignmentList.size(); i++) {
            System.out.println((i + 1) + ". " + assignmentList.get(i).getTitle());
        }
        System.out.println("0. 이전으로 돌아가기");
    }

    public void printStudentAssignmentDetail(StudentAssignmentDTO dto) {
        if (dto == null) {
            System.out.println("선택한 과제 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n=============== 선택한 과제 정보 ===============");
        System.out.println("강의명       : " + dto.getCourseTitle());
        System.out.println("과제명       : " + dto.getTitle());
        System.out.println("과제 내용    : " + dto.getDescription());
        System.out.println("마감일       : " + dto.getDeadline());
        System.out.println("제출 상태    : " + dto.getSubmissionStatus());
        System.out.println("과제 제출 명 : " + (dto.getSubmissionContent() == null ? "-" : dto.getSubmissionContent()));
        System.out.println("제출일시     : " + (dto.getSubmittedAt() == null ? "-" : dto.getSubmittedAt()));
        System.out.println("=======================================================");
    }

    public void printProfessorCourseMenu(Map<Long, String> courseMap) {
        if (courseMap == null || courseMap.isEmpty()) {
            System.out.println("담당 중인 강의가 없습니다.");
            return;
        }

        System.out.println("강의를 선택해주세요.");
        int index = 1;
        for (String courseTitle : courseMap.values()) {
            System.out.println(index + ". " + courseTitle);
            index++;
        }
        System.out.println("0. 이전으로 돌아가기");
    }

    public void printProfessorAssignmentMenu(String courseTitle, List<ProfessorAssignmentDTO> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("선택한 강의의 과제가 없습니다.");
            return;
        }

        System.out.println("[" + courseTitle + "]의 과제를 선택해주세요.");
        for (int i = 0; i < assignmentList.size(); i++) {
            System.out.println((i + 1) + ". " + assignmentList.get(i).getAssignmentTitle());
        }
        System.out.println("0. 이전으로 돌아가기");
    }

    public void printProfessorAssignmentDetail(ProfessorAssignmentDTO dto) {
        if (dto == null) {
            System.out.println("선택한 과제 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n=============== 선택한 과제 정보 ===============");
        System.out.println("강의명     : " + dto.getCourseTitle());
        System.out.println("과제명     : " + dto.getAssignmentTitle());
        System.out.println("과제 내용  : " + dto.getDescription());
        System.out.println("마감일     : " + dto.getDeadline());
        System.out.println("======================================================");
    }

    public void printProfessorAssignments(List<ProfessorAssignmentDTO> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("조회된 과제가 없습니다.");
            return;
        }

        System.out.println("=============== 내가 생성한 과제 조회 ===============");
        for (ProfessorAssignmentDTO dto : assignmentList) {
            System.out.println("과제번호 : " + dto.getAssignmentId());
            System.out.println("강의번호 : " + dto.getCourseId());
            System.out.println("강의명   : " + dto.getCourseTitle());
            System.out.println("과제명   : " + dto.getAssignmentTitle());
            System.out.println("설명     : " + dto.getDescription());
            System.out.println("마감일   : " + dto.getDeadline());
            System.out.println("=====================================================");
        }
        System.out.println(" ========✅생성한 과제를 조회했습니다✅========");
    }


    public void printProfessorSubmissionStatus(List<ProfessorAssignmentSubmissionDTO> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("제출 현황이 없습니다.");
            return;
        }

        System.out.println("=============== 학생 제출 현황 조회 ===============");
        for (ProfessorAssignmentSubmissionDTO dto : list) {
            System.out.println("학생ID   : " + dto.getStudentId());
            System.out.println("학생이름 : " + dto.getStudentName());
            System.out.println("제출여부 : " + dto.getSubmissionStatus());
            System.out.println("제출내용 : " + (dto.getContentTitle() == null ? "-" : dto.getContentTitle()));
            System.out.println("제출일시 : " + (dto.getSubmittedAt() == null ? "-" : dto.getSubmittedAt()));
            System.out.println("=================================================");
        }
        System.out.println(" ========✅학생 과제 제출 현황을 조회했습니다!✅========");
    }
}