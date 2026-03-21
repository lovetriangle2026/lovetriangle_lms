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
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        int professorId = loginUser.getId();

        while (true) {
            List<ProfessorCourseDTO> courseList = controller.findCoursesByProfessorId(professorId);

            if (courseList == null || courseList.isEmpty()) {
                outputView.printError("담당 강의가 없습니다.");
                return;
            }

            System.out.println();
            System.out.println("=================================");
            System.out.println("         교수 출결관리");
            System.out.println("=================================");
            System.out.println("강의를 선택하여 출결 조회 및 수정을 진행하세요.");

            for (int i = 0; i < courseList.size(); i++) {
                System.out.println((i + 1) + ". " + courseList.get(i).getTitle());
            }
            System.out.println("0. 뒤로가기");
            System.out.print("번호를 입력해주세요 : ");
            int choice = inputInt();

            if (choice == 0) {
                outputView.printMessage("이전 메뉴로 돌아갑니다.");
                return;
            }

            if (choice < 1 || choice > courseList.size()) {
                outputView.printError("잘못된 번호입니다.");
                continue;
            }

            ProfessorCourseDTO selectedCourse = courseList.get(choice - 1);
            displayCourseAttendanceMenu(selectedCourse.getId(), selectedCourse.getTitle(), professorId);
        }
    }

    private void displayCourseAttendanceMenu(int courseId, String courseTitle, int professorId) {
        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println(" [" + courseTitle + "] 출결관리 메뉴");
            System.out.println("=================================");
            System.out.println("1. 전체 출결 조회");
            System.out.println("2. 주차별 출결 조회");
            System.out.println("3. 출결 유형별 조회");
            System.out.println("4. 출결 수정");
            System.out.println("0. 강의 선택으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findAttendanceByCourseId(courseId, professorId);
                    break;
                case 2:
                    findAttendanceByCourseIdAndWeek(courseId, professorId);
                    break;
                case 3:
                    findAttendanceByCourseIdAndStatus(courseId, professorId);
                    break;
                case 4:
                    outputView.printMessage("출결 수정 기능은 아직 구현 전입니다.");
                    break;
                case 0:
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void findAttendanceByCourseId(int courseId, int professorId) {
        List<AttendanceDTO> attendanceList =
                controller.findAttendanceByCourseId(courseId, professorId);

        if (attendanceList == null || attendanceList.isEmpty()) {
            outputView.printError("해당 강의의 출결 데이터가 없습니다.");
            return;
        }

        outputView.printAttendanceByWeek(attendanceList);
    }

    private void findAttendanceByCourseIdAndWeek(int courseId, int professorId) {
        System.out.print("조회할 주차를 입력해주세요 : ");
        int week = inputInt();

        List<AttendanceDTO> attendanceList =
                controller.findAttendanceByCourseIdAndWeek(courseId, professorId, week);

        if (attendanceList == null || attendanceList.isEmpty()) {
            outputView.printError("해당 강의의 해당 주차 출결 데이터가 없습니다.");
            return;
        }

        outputView.printAttendanceList(attendanceList);
    }

    private void findAttendanceByCourseIdAndStatus(int courseId, int professorId) {
        System.out.print("출결 상태를 입력해주세요 (PRESENT / LATE / ABSENT / EXCUSED) : ");
        String status = sc.nextLine().toUpperCase();

        List<AttendanceDTO> attendanceList =
                controller.findAttendanceByCourseIdAndStatus(courseId, professorId, status);

        if (attendanceList == null || attendanceList.isEmpty()) {
            outputView.printError("해당 강의의 해당 출결 유형 데이터가 없습니다.");
            return;
        }

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