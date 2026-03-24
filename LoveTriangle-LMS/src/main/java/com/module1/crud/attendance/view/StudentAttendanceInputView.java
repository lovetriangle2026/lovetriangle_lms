package com.module1.crud.attendance.view;

import com.module1.crud.attendance.controller.AttendanceController;
import com.module1.crud.attendance.controller.AttendanceStatusConverter;
import com.module1.crud.attendance.model.dto.AttendanceDTO;
import com.module1.crud.attendance.model.dto.ProfessorCourseDTO;
import com.module1.crud.attendance.model.dto.SessionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

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
            System.out.println("\n======= [학생 출결관리 메뉴] =======");
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
                    checkAttendance();
                    break;
                case 3:
                    requestExcused();
                    break;
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    public void findMyAttendance() {
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        int studentId = loginUser.getId();

        List<ProfessorCourseDTO> courseList = controller.findCoursesByStudentId(studentId);

        if (courseList == null || courseList.isEmpty()) {
            outputView.printError("수강 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n======= [수강 강의 목록] =======");
        for (int i = 0; i < courseList.size(); i++) {
            System.out.println((i + 1) + ". " + courseList.get(i).getTitle());
        }

        System.out.println("0. 돌아가기");
        System.out.print("선택해주세요 : ");
        int choice = inputInt();

        if (choice == 0) {
            outputView.printMessage("이전 메뉴로 돌아갑니다.");
            return;
        }

        if (choice < 1 || choice > courseList.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        int courseId = courseList.get(choice - 1).getId();

        List<AttendanceDTO> attendanceList =
                controller.findAttendanceByStudentIdAndCourseId(studentId, courseId);

        if (attendanceList == null || attendanceList.isEmpty()) {
            outputView.printError("해당 강의의 출결 데이터가 없습니다.");
            return;
        }

        outputView.printAttendanceByWeek(attendanceList);
    }

    private void checkAttendance() {
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        int studentId = loginUser.getId();

        List<SessionDTO> sessionList = controller.findAvailableSessionByStudentId(studentId);

        if (sessionList == null || sessionList.isEmpty()) {
            outputView.printError("현재 출석 가능한 수업이 없습니다.");
            return;
        }

        SessionDTO selectedSession;

        if (sessionList.size() == 1) {
            selectedSession = sessionList.get(0);
        } else {
            System.out.println("\n===== [현재 출석 가능한 수업 목록] =====");
            System.out.println("0. 이전으로 돌아가기");

            for (int i = 0; i < sessionList.size(); i++) {
                SessionDTO session = sessionList.get(i);
                System.out.println((i + 1) + ". "
                        + session.getCourseTitle()
                        + " - "
                        + session.getWeek() + "주차");
            }

            System.out.print("선택해주세요 : ");
            int choice = inputInt();

            if (choice == 0) {
                outputView.printMessage("이전 메뉴로 돌아갑니다.");
                return;
            }

            if (choice < 1 || choice > sessionList.size()) {
                outputView.printError("잘못된 번호입니다.");
                return;
            }

            selectedSession = sessionList.get(choice - 1);
        }

        System.out.print("[" + selectedSession.getCourseTitle() + "] "
                + selectedSession.getWeek() + "주차 수업에 출석체크를 하시겠습니까? (Y/N) : ");

        String confirm = sc.nextLine().trim().toUpperCase();

        if (!confirm.equals("Y")) {
            outputView.printMessage("이전 메뉴로 돌아갑니다.");
            return;
        }

        try {
            String resultMessage = controller.checkAttendance(studentId, selectedSession);

            if (resultMessage.startsWith("출석체크 완료")) {
                outputView.printSuccess(resultMessage);
            } else {
                outputView.printError(resultMessage);
            }

        } catch (Exception e) {
            outputView.printError("출석체크 중 오류가 발생했습니다: " + e.getMessage());
        }
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

    private void requestExcused() {
        UsersDTO loginUser = SessionManager.getInstance().getLoggedInUser();
        int studentId = loginUser.getId();

        List<ProfessorCourseDTO> courseList = controller.findCoursesByStudentId(studentId);

        if (courseList == null || courseList.isEmpty()) {
            outputView.printError("수강 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n======= [수강 강의 목록] =======");

        for (int i = 0; i < courseList.size(); i++) {
            System.out.println((i + 1) + ". " + courseList.get(i).getTitle());
        }

        System.out.println("0. 돌아가기");
        System.out.print("공결 신청할 강의를 선택해주세요 : ");
        int courseChoice = inputInt();

        if (courseChoice == 0) {
            outputView.printMessage("이전 메뉴로 돌아갑니다.");
            return;
        }

        if (courseChoice < 1 || courseChoice > courseList.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        ProfessorCourseDTO selectedCourse = courseList.get(courseChoice - 1);
        int courseId = selectedCourse.getId();

        List<SessionDTO> sessionList = controller.findSessionsByCourseId(courseId);

        if (sessionList == null || sessionList.isEmpty()) {
            outputView.printError("해당 강의의 주차 정보가 없습니다.");
            return;
        }

        System.out.println("\n========= [주차 목록] =========");
        System.out.println("0. 돌아가기");

        for (int i = 0; i < sessionList.size(); i++) {
            System.out.println((i + 1) + ". " + sessionList.get(i).getWeek() + "주차");
        }

        System.out.print("공결 신청할 주차를 선택해주세요 : ");
        int sessionChoice = inputInt();

        if (sessionChoice == 0) {
            outputView.printMessage("이전 메뉴로 돌아갑니다.");
            return;
        }

        if (sessionChoice < 1 || sessionChoice > sessionList.size()) {
            outputView.printError("잘못된 번호입니다.");
            return;
        }

        SessionDTO selectedSession = sessionList.get(sessionChoice - 1);

        System.out.print("정말 공결 신청하시겠습니까? (Y/N) : ");
        String confirm = sc.nextLine().trim().toUpperCase();

        if (!confirm.equals("Y")) {
            outputView.printMessage("공결 신청이 취소되었습니다.");
            return;
        }

        boolean result = controller.applyExcuseRequest(studentId, selectedSession.getId());

        if (result) {
            outputView.printSuccess("공결 신청이 완료되었습니다. 현재 상태: 공결 신청 대기 중");
        } else {
            outputView.printError("공결 신청에 실패했습니다.");
        }
    }


}