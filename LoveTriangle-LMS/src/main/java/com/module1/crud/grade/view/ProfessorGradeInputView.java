package com.module1.crud.grade.view;

import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;
import java.util.Scanner;

public class ProfessorGradeInputView {


    private final GradeController controller;
    private final ProfessorGradeOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public ProfessorGradeInputView(GradeController controller, ProfessorGradeOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayProfessorMainMenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         교수 성적관리 메뉴");
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
                    outputView.printmessage("== 실습을 종료합니다. ==");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void displayGradeStatus() {
        System.out.println("=================================");
        outputView.printmessage("\"교수 성적관리 메뉴\"");
        System.out.println("=================================");
        System.out.println("1. 전체 조회");
        System.out.println("2. 학생 조회");
        System.out.print("번호를 입력해주세요 : ");

        int s = inputInt();

          if(s==1){
              outputView.printError("아직 구현 중인 기능입니다.");}

          if(s==2){
              UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
              if (loggedInUser == null) {
                  outputView.printError("로그인 정보가 없습니다. 다시 로그인해주세요.");
                  return;
              }
              long professor = loggedInUser.getId();
              System.out.print("조회할 학생 이름을 입력해주세요 : ");
              String studentName = sc.nextLine();

              List<GradeViewDTO> oneGradeList = controller.handlefindgrade(professor,studentName);
              outputView.printstudentGrades(oneGradeList);
          }

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

