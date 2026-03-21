package com.module1.crud.grade.view;

import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;
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
            System.out.println("2. 성적 수정");
            System.out.println("3. 성적 등록");
            System.out.println("4. ??");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    displayGradeStatus();
                    break;
                case 2:
                    displayupdategrade();
                    break;
                case 3:
                    displayRegisterGrade();
                    break;
                case 4:
                    outputView.printmessage("== 실습을 종료합니다. ==");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void displayupdategrade() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            outputView.printError("로그인 정보가 없습니다. 다시 로그인해주세요.");
            return;
        }

        long professorId = loggedInUser.getId();

        System.out.println("=================================");
        System.out.println("          성적 수정 메뉴");
        System.out.println("=================================");
        System.out.println("1. 중간고사 점수 수정");
        System.out.println("2. 기말고사 점수 수정");
        System.out.println("3. 과제 점수 수정");
        System.out.print("번호를 입력해주세요 : ");

        int editMenu = inputInt();

        List<GradeEditDTO> gradeList = controller.getEditableGradeList(professorId);

        if (gradeList == null || gradeList.isEmpty()) {
            outputView.printError("수정 가능한 성적 정보가 없습니다.");
            return;
        }

        outputView.printEditableGradeList(gradeList);

        System.out.print("수정할 번호를 입력해주세요 : ");
        int choice = inputInt();

        if (choice < 1 || choice > gradeList.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        GradeEditDTO selected = gradeList.get(choice - 1);

        System.out.println("선택한 학생 : " + selected.getStudentName());
        System.out.println("과목 : " + selected.getCourseTitle());

        System.out.println("새 점수를 입력해주세요 : ");
        int newScore = inputInt();

        int result = 0;

        switch (editMenu) {
            case 1:
                result = controller.updateMidtermScore(
                        selected.getStudentId(),
                        selected.getCourseId(),
                        newScore
                );
                break;
            case 2:
                result = controller.updateFinalScore(
                        selected.getStudentId(),
                        selected.getCourseId(),
                        newScore
                );
                break;
            case 3:
                result = controller.updateAssignmentScore(
                        selected.getStudentId(),
                        selected.getCourseId(),
                        newScore
                );
                break;
            default:
                outputView.printError("잘못된 메뉴 선택입니다.");
                return;
        }

        if (result > 0) {
            outputView.printmessage("성적 수정이 완료되었습니다.");
        } else {
            outputView.printError("성적 수정에 실패했습니다.");
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

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            outputView.printError("로그인 정보가 없습니다. 다시 로그인해주세요.");
            return;
        }

        long professorId = loggedInUser.getId();


        if (s == 1) {
            List<GradeViewDTO> gradeList = controller.viewallgradeByprofessor(professorId);
            outputView.printstudentGrades(gradeList);

        } else if (s == 2) {
            System.out.print("조회할 학생 이름을 입력해주세요 : ");
            String studentName = sc.nextLine();

            List<GradeViewDTO> oneGradeList = controller.handlefindgrade(professorId, studentName);
            outputView.printstudentGrades(oneGradeList);

        } else {
            outputView.printError("다시 선택해주세요.");
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

    private void displayRegisterGrade() {

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            outputView.printError("로그인 정보가 없습니다.");
            return;
        }

        long professorId = loggedInUser.getId();

        while (true) {
            System.out.println("=================================");
            System.out.println("          성적 등록 메뉴");
            System.out.println("=================================");
            System.out.println("1. 과제 평가 등록");
            System.out.println("2. 중간고사 점수 등록");
            System.out.println("3. 기말고사 점수 등록");
            System.out.println("4. 뒤로가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    handleAssignmentRegister(professorId);
                    break;
                case 2:
                    handleMidtermRegister(professorId);
                    break;
                case 3:
                    handleFinalRegister(professorId);
                    break;
                case 4:
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void handleFinalRegister(long professorId) {
    }

    private void handleMidtermRegister(long professorId) {
        List<GradeRegisterDTO> list = controller.getMidtermRegisterTargets(professorId);

        if (list == null || list.isEmpty()) {
            outputView.printError("등록할 대상이 없습니다.");
            return;
        }

        outputView.printAssignmentRegisterTargets(list); // 재사용 가능

        System.out.print("번호를 선택하세요 : ");
        int choice = inputInt();

        if (choice < 1 || choice > list.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        GradeRegisterDTO selected = list.get(choice - 1);

        System.out.println("학생 : " + selected.getStudentName());
        System.out.println("과목 : " + selected.getCourseTitle());

        System.out.print("중간고사 점수 입력 : ");
        int score = inputInt();

        int result = controller.registerMidtermScore(
                selected.getStudentId(),
                selected.getCourseId(),
                score
        );

        if (result > 0) {
            outputView.printmessage("중간고사 등록 완료 🎉");
        } else {
            outputView.printError("등록 실패 🚨");
        }
    }

    private void handleAssignmentRegister(long professorId) {
        List<GradeRegisterDTO> list = controller.getAssignmentRegisterTargets(professorId);

        if (list == null || list.isEmpty()) {
            outputView.printError("등록할 대상이 없습니다.");
            return;
        }

        outputView.printAssignmentRegisterTargets(list);

        System.out.print("번호를 선택하세요 : ");
        int choice = inputInt();

        if (choice < 1 || choice > list.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        GradeRegisterDTO selected = list.get(choice - 1);

        System.out.println("학생 : " + selected.getStudentName());
        System.out.println("과목 : " + selected.getCourseTitle());

        System.out.print("점수 입력 : ");
        int score = inputInt();

        int result = controller.registerAssignmentScore(
                selected.getStudentId(),
                selected.getCourseId(),
                score
        );

        if (result > 0) {
            outputView.printmessage("등록 완료 🎉");
        } else {
            outputView.printError("등록 실패 🚨");
        }
    }
}

