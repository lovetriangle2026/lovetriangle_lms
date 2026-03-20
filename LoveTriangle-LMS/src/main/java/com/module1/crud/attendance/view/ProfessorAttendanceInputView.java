package com.module1.crud.attendance.view;

import com.module1.crud.attendance.controller.AttendanceController;
import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;
import java.util.Scanner;

public class ProfessorAttendanceInputView {

    private final AttendanceController controller;
    private final AttendanceOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public ProfessorAttendanceInputView(AttendanceController controller, AttendanceOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayMenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("        교수 출결관리 메뉴");
            System.out.println("=================================");
            System.out.println("1. 강의별 출결 조회");
            System.out.println("2. 주차별 출결 조회");
            System.out.println("3. 출결 유형별 조회");
            System.out.println("4. 출결 수정");
            System.out.println("0. 뒤로가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findAttendanceByCourseId();
                    break;
                case 2:
                    findAttendanceByWeek();
                    break;
                case 3:
                    findAttendanceByStatus();
                    break;
                case 4:
                    outputView.printMessage("출결 수정 기능은 아직 구현 전입니다.");
                    break;
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void findAttendanceByCourseId() {
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        int professorId = loginUser.getId();

        List<ProfessorCourseDTO> courseList = controller.findCoursesByProfessorId(professorId);

        if (courseList == null || courseList.isEmpty()) {
            outputView.printError("담당 강의가 없습니다.");
            return;
        }

        System.out.println("\n===== 담당 강의 목록 =====");

        for (ProfessorCourseDTO course : courseList) {
            System.out.println(course.getId() + ". " + course.getTitle());
        }
        System.out.println("0. 돌아가기");
        System.out.print("선택해주세요 : ");
        int courseId = inputInt();

        if (courseId == 0) {
            outputView.printMessage("이전 메뉴로 돌아갑니다.");
            return;
        }

        List<AttendanceDTO> attendanceList =
                controller.findAttendanceByCourseId(courseId, professorId);

        if (attendanceList == null || attendanceList.isEmpty()) {
            outputView.printError("담당 강의가 아니거나 조회된 출결 데이터가 없습니다.");
            return;
        }

        outputView.printAttendanceByWeek(attendanceList);
    }


    private void findAttendanceByWeek() {
        System.out.print("조회할 주차를 입력해주세요 : ");
        int week = inputInt();

        List<AttendanceDTO> attendanceList = controller.findAttendanceByWeek(week);

        outputView.printAttendanceList(attendanceList);
    }

    private void findAttendanceByStatus() {
        System.out.print("출결 상태를 입력해주세요 (PRESENT / LATE / ABSENT / EXCUSED) : ");
        String status = sc.nextLine().toUpperCase();

        List<AttendanceDTO> attendanceList = controller.findAttendanceByStatus(status);

        outputView.printAttendanceList(attendanceList);
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}