package com.module1.crud.grade.view;

import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.util.List;
import java.util.Scanner;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
public class StudentGradeInputView {
    private final GradeController controller;
    private final StudentGradeOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    // 생성자를 통한 final 변수 초기화
    public StudentGradeInputView(GradeController controller, StudentGradeOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayStudentMainMenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         학생 성적관리 메뉴");
            System.out.println("=================================");
            System.out.println("1. 성적 조회");
            System.out.println("2. ??");
            System.out.println("3. ??");
            System.out.println("4. ??");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    displayGradeStatus();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    outputView.printMessage("== 실습을 종료합니다. ==");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }


    }

    private void displayGradeStatus() {
        outputView.printMessage("\n--- [내 성적 조회] ---");
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        if (loginUser == null) {
            outputView.printError("로그인 정보가 없습니다.");
            return;}
        long studentId = loginUser.getId();
        List<GradeViewDTO> GradeList = controller.findAllGrade(studentId);
        outputView.printGrades(GradeList);
    }

    private int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }

    private long inputLong() {
        while (true) {
            try {
                long value = Long.parseLong(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}
