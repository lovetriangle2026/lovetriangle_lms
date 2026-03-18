package com.module1.crud.attendance.view;

import com.module1.crud.attendance.controller.AttendanceController;
import com.module1.crud.attendance.model.dto.AttendanceDTO;

import java.util.List;
import java.util.Scanner;

public class StudentAttendanceInputView {

    private final AttendanceController controller;
    private final AttendanceOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public StudentAttendanceInputView(AttendanceController controller, AttendanceOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayMenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("        학생 출결관리 메뉴");
            System.out.println("=================================");
            System.out.println("1. 내 출결 조회");
            System.out.println("2. 출석하기");
            System.out.println("3. 공결 신청");
            System.out.println("0. 뒤로가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findMyAttendance();
                    break;
                case 2:
                    outputView.printMessage("출석하기 기능은 아직 구현 전입니다.");
                    break;
                case 3:
                    outputView.printMessage("공결 신청 기능은 아직 구현 전입니다.");
                    break;
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void findMyAttendance() {
        System.out.print("학생 ID를 입력해주세요 : ");
        int studentId = inputInt();

        List<AttendanceDTO> attendanceList = controller.findAttendanceByStudentId(studentId);

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