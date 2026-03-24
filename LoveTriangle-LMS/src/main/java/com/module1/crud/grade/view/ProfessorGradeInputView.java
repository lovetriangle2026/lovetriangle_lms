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
            System.out.println("========= [교수 성적관리 메뉴] =========");
            System.out.println("1. 성적 조회");
            System.out.println("2. 성적 수정");
            System.out.println("3. 성적 등록");
            System.out.println("0. 이전으로 돌아가기");
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
                case 0:
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

        while (true) {
            System.out.println("========= [교수 성적수정 메뉴] =========");
            System.out.println("1. 중간고사 점수 수정");
            System.out.println("2. 기말고사 점수 수정");
            System.out.println("3. 과제 점수 수정");
            System.out.println("0. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int editMenu = inputInt();

            if (editMenu == 0) {
                return;
            }

            if (editMenu < 0 || editMenu > 3) {
                outputView.printError("잘못된 메뉴 선택입니다. 다시 입력해주세요.");
                continue;
            }

            List<GradeEditDTO> gradeList = controller.getEditableGradeList(professorId);

            if (gradeList == null || gradeList.isEmpty()) {
                outputView.printError("수정 가능한 성적 정보가 없습니다.");
                continue;
            }

            outputView.printEditableGradeList(gradeList);

            int choice;
            while (true) {
                System.out.print("수정할 번호를 입력해주세요 : ");
                choice = inputInt();

                if (choice >= 1 && choice <= gradeList.size()) {
                    break;
                }

                outputView.printError("잘못된 번호입니다. 다시 입력해주세요.");
            }

            GradeEditDTO selected = gradeList.get(choice - 1);

            System.out.println("선택한 학생 : " + selected.getStudentName());
            System.out.println("과목 : " + selected.getCourseTitle());

            int newScore;
            while (true) {
                System.out.print("새 점수를 입력해주세요 : ");
                newScore = inputInt();

                if (newScore >= 0 && newScore <= 100) {
                    break;
                }

                outputView.printError("점수는 0~100 사이로 입력해주세요.");
            }

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
            }

            if (result > 0) {
                System.out.println("수정 성공!");
                List<GradeEditDTO> updatedGradeList = controller.getEditableGradeList(professorId);
                outputView.printEditableGradeList(updatedGradeList);
            } else {
                System.out.println("수정 실패!");
            }
        }
    }


    private void displayGradeStatus() {

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            outputView.printError("로그인 정보가 없습니다. 다시 로그인해주세요.");
            return;
        }

        long professorId = loggedInUser.getId();

        while (true) {
            System.out.println("========= 교수 조회 메뉴 =========");
            System.out.println("1. 전체 조회");
            System.out.println("2. 학생 조회");
            System.out.println("0. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int s = inputInt();

            if (s == 1) {
                List<GradeViewDTO> gradeList = controller.viewallgradeByprofessor(professorId);
                outputView.printstudentGrades(gradeList);

            } else if (s == 2) {
                System.out.print("조회할 학생 이름을 입력해주세요 : ");
                String studentName = sc.nextLine();

                List<GradeViewDTO> oneGradeList = controller.handlefindgrade(professorId, studentName);
                outputView.printstudentGrades(oneGradeList);

            } else if (s == 0) {
                return;
            } else {
                outputView.printError("다시 선택해주세요.");
            }
            System.out.println();
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
            System.out.println("========= [교수 성적등록 메뉴] =========");
            System.out.println("1. 과제 평가 등록");
            System.out.println("2. 중간고사 점수 등록");
            System.out.println("3. 기말고사 점수 등록");
            System.out.println("0. 이전으로 돌아가기");
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
                case 0:
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void handleFinalRegister(long professorId) {
        List<GradeRegisterDTO> list = controller.getFinalRegisterTargets(professorId);

        if (list == null || list.isEmpty()) {
            outputView.printError("등록할 대상이 없습니다.");
            return;
        }

        outputView.printAssignmentRegisterTargets(list);

        int choice;
        while (true) {
            System.out.print("번호를 선택하세요 : ");
            choice = inputInt();

            if (choice >= 1 && choice <= list.size()) {
                break;
            }

            outputView.printError("잘못된 번호입니다. 다시 입력해주세요.");
        }

        GradeRegisterDTO selected = list.get(choice - 1);

        System.out.println("학생 : " + selected.getStudentName());
        System.out.println("과목 : " + selected.getCourseTitle());

        int score;
        while (true) {
            System.out.print("기말고사 점수 입력 (0~100) : ");
            score = inputInt();

            if (score >= 0 && score <= 100) {
                break;
            }

            outputView.printError("0~100 사이의 숫자만 입력하세요.");
        }

        int result = controller.registerFinalScore(
                selected.getStudentId(),
                selected.getCourseId(),
                score
        );

        if (result > 0) {
            outputView.printmessage("기말고사 등록 완료 🎉");
            List<GradeEditDTO> updatedGradeList = controller.getEditableGradeList(professorId);
            outputView.printEditableGradeList(updatedGradeList);// ⭐ 수정됨
        } else {
            outputView.printError("등록 실패 🚨");
        }
    }

    private void handleMidtermRegister(long professorId) {
        List<GradeRegisterDTO> list = controller.getMidtermRegisterTargets(professorId);

        if (list == null || list.isEmpty()) {
            outputView.printError("등록할 대상이 없습니다.");
            return;
        }

        outputView.printAssignmentRegisterTargets(list);

        int choice;
        while (true) {
            System.out.print("번호를 선택하세요 : ");
            choice = inputInt();

            if (choice >= 1 && choice <= list.size()) {
                break;
            }

            outputView.printError("잘못된 번호입니다. 다시 입력해주세요.");
        }

        GradeRegisterDTO selected = list.get(choice - 1);

        System.out.println("학생 : " + selected.getStudentName());
        System.out.println("과목 : " + selected.getCourseTitle());

        int score;
        while (true) {
            System.out.print("중간고사 점수 입력 (0~100) : ");
            score = inputInt();

            if (score >= 0 && score <= 100) {
                break;
            }

            outputView.printError("0~100 사이의 숫자만 입력하세요.");
        }

        int result = controller.registerMidtermScore(
                selected.getStudentId(),
                selected.getCourseId(),
                score
        );

        if (result > 0) {
            outputView.printmessage("중간고사 등록 완료 🎉");
            List<GradeEditDTO> updatedGradeList = controller.getEditableGradeList(professorId);
            outputView.printEditableGradeList(updatedGradeList);
        } else {
            outputView.printError("등록 실패 🚨");
        }
    }

    private void handleAssignmentRegister(long professorId) {
        List<GradeRegisterDTO> registerList = controller.getAssignmentRegisterTargets(professorId);

        if (registerList == null || registerList.isEmpty()) {
            outputView.printError("등록 가능한 과제 평가 정보가 없습니다.");
            return;
        }

        outputView.printAssignmentRegisterTargets(registerList);

        System.out.print("등록할 번호를 입력해주세요 : ");
        int choice = inputInt();

        if (choice < 1 || choice > registerList.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        GradeRegisterDTO selected = registerList.get(choice - 1);

        int score;
        while (true) {
            System.out.print("과제 점수를 입력해주세요 (0~100) : ");
            score = inputInt();

            if (score >= 0 && score <= 100) {
                break;
            }

            outputView.printError("0~100 사이의 숫자만 입력하세요.");
        }

        int result = controller.registerAssignmentScore(
                selected.getStudentId(),
                selected.getCourseId(), // 2번째로 이동
                score                   // 3번째로 이동
        );

        if (result > 0) {
            outputView.printmessage("과제 점수 등록 성공!");
            List<GradeEditDTO> updatedGradeList = controller.getEditableGradeList(professorId);
            outputView.printEditableGradeList(updatedGradeList);
        } else {
            outputView.printError("과제 점수 등록 실패!");
        }
    }
}

