package com.module1.crud.grade.view;

import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.dto.GradeViewDTO;

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
        List<GradeViewDTO> GradeList = controller.findAllCourses();
        outputView.printGrades(GradeList);}

          if(s==2){
              long professor = 1;
              String studentname = "박만서";
              List<GradeViewDTO> oneGradeList = controller.handlefindgrade(professor,studentname);
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

