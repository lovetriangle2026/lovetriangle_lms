package com.module1.crud.users.view;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.dto.UsersDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UsersInputView {


    private final UsersController controller;
    private final UsersOutputView outputView;
    private final Scanner sc = new Scanner(System.in);


    public UsersInputView(UsersController controller, UsersOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }


    // 2. 학생 전용 메인 페이지 (기존 코드를 리팩토링)
    public void stutMainPage(UsersDTO user) {
        while (true) {
            System.out.println("\n================ [ 학생 메인 메뉴 ] ================");
            System.out.println("      ━━━━━━━");
            System.out.println("     ┃  ●  ●  ┃");
            System.out.println("     ┃    ▽   ┃  [ " + user.getName() + " ] 님 환영합니다.");
            System.out.println("      ━━━━━━━    ID: " + user.getLoginId() + " (" + user.getUserType() + ")");
            System.out.println("                 Code: " + user.getUserCode());
            System.out.println("======================================================");
            System.out.println(" 1. 회원정보 수정");
            System.out.println(" 2. 수강내역 조회");
            System.out.println(" 3. 나의 과제 조회");
            System.out.println(" 4. 나의 출결 조회");
            System.out.println(" 9. 회원 탈퇴 ");
            System.out.println(" 0. 로그아웃 및 메인메뉴로 돌아가기");
            System.out.println("======================================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    updateUserInfo(user);
                case "2": /* courseHistory(); */ break;
                case "3": /* myAssignments(); */ break;
                case "4": /* myAttendance(); */ break;
                case "9":
                    deleteMyAccount(user.getId());
                    return;
                case "0":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 3. 교수 전용 메인 페이지 (신규 생성)
    public void profMainPage(UsersDTO user) {
        while (true) {
            System.out.println("\n================ [ 교수 메인 메뉴 ] ================");
            System.out.println("      ━━━━━━━");
            System.out.println("     ┃  ■  ■  ┃");
            System.out.println("     ┃   ──   ┃  [ " + user.getName() + " ] 교수님 환영합니다.");
            System.out.println("      ━━━━━━━    ID: " + user.getLoginId() + " (" + user.getUserType() + ")");
            System.out.println("                 Code: " + user.getUserCode());
            System.out.println("======================================================");
            System.out.println(" 1. 회원정보 수정");
            System.out.println(" 2. 강의내역 조회");
            System.out.println(" 3. 과제 관리 이동");
            System.out.println(" 4. 출결 관리 이동");
            System.out.println(" 9. 회원 탈퇴 ");
            System.out.println(" 0. 뒤로가기");
            System.out.println("======================================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    updateUserInfo(user);
                case "2": /* lectureHistory(); */ break;
                case "3": /* manageAssignments(); */ break;
                case "4": /* manageAttendance(); */ break;
                case "9":
                    deleteMyAccount(user.getId());
                    return;
                case "0":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }


    private void displayUsersList() {
        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");

        List<UsersDTO> courseList = controller.findAllUsers();

        outputView.printCourses(courseList);
    }

    public void createUser() {
        System.out.println("\n=== 📝 신규 회원 가입 ===");

        System.out.print("사번/학번(User Code)을 입력해주세요 : ");
        String userCode = sc.nextLine();

        System.out.print("로그인 ID를 입력해주세요 : ");
        String loginId = sc.nextLine();

        System.out.print("이름을 입력해주세요 : ");
        String name = sc.nextLine();

        System.out.print("생년월일 (YYYY-MM-DD 형식)을 입력해주세요 : ");
        String birthInput = sc.nextLine();
        // 💡 문자열 입력을 LocalDate 객체로 변환합니다.
        LocalDate birth = LocalDate.parse(birthInput);

        System.out.print("전화번호를 입력해주세요 : ");
        String telNum = sc.nextLine();

        System.out.print("비밀번호를 입력해주세요 : ");
        String password = sc.nextLine();

        System.out.print("비밀번호 찾기 정답을 입력해주세요 : ");
        String pwAnswer = sc.nextLine();

        System.out.print("사용자 유형 (STUDENT / PROFESSOR)을 입력해주세요 : ");
        String userType = sc.nextLine();

        /* comment.
         * 입력받은 원시 데이터들을 Controller로 전달하여 처리를 위임합니다.
         * DB 처리 결과로 생성된 회원의 고유 ID(PK)가 반환됩니다.
         * */
        Long result = controller.createUser(userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        if (result != null && result > 0) {
            outputView.printSuccess("회원 가입 성공! 부여된 회원 고유 ID : " + result);
        } else {
            outputView.printError("회원 가입에 실패했습니다. 입력 정보를 확인해주세요.");
        }
    }

    private void deleteMyAccount(int loggedInUserId) {
        System.out.println("\n=== 🗑️ 회원 탈퇴 ===");
        System.out.print("정말 탈퇴하시겠습니까? 탈퇴 시 모든 데이터가 삭제됩니다. (Y/N) : ");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            // Controller를 호출하여 삭제 수행
            boolean isDeleted = controller.deleteUser(loggedInUserId);

            if (isDeleted) {
                outputView.printSuccess("회원 탈퇴가 완료되었습니다. 그동안 이용해 주셔서 감사합니다.");
                SessionManager.getInstance().clearSession();
            } else {
                outputView.printError("회원 탈퇴에 실패했습니다. 관리자에게 문의하세요.");
            }
        } else {
            System.out.println("탈퇴를 취소하고 이전 화면으로 돌아갑니다.");
        }
    }

    private void updateUserInfo(UsersDTO user) {
        while (true) {
            System.out.println("\n================ [ 회원정보 수정 ] ================");
            System.out.println(" [현재 내 정보]");
            System.out.println(" - 이름: " + user.getName());
            System.out.println(" - 전화번호: " + user.getTelNum());
            System.out.println(" - 비밀번호 찾기 답: " + user.getPwAnswer());
            System.out.println(" - 비밀번호: **** (보안상 숨김처리)");
            System.out.println(" ※ ID, 학번/사번, 생년월일, 소속(" + user.getUserType() + ")은 변경할 수 없습니다.");
            System.out.println("======================================================");
            System.out.println(" 1. 비밀번호 변경");
            System.out.println(" 2. 전화번호 변경");
            System.out.println(" 3. 이름 변경");
            System.out.println(" 4. 비밀번호 힌트 답 변경");
            System.out.println(" 0. 수정 종료 및 돌아가기");
            System.out.println("======================================================");
            System.out.print("▶ 수정할 항목 선택: ");

            String choice = sc.nextLine();

            if (choice.equals("0")) {
                System.out.println("회원정보 수정을 종료합니다.");
                return;
            }

            // 기존 정보를 담은 복사본을 만들어 수정할 부분만 덮어씌웁니다.
            UsersDTO updatedUser = new UsersDTO(
                    user.getId(), user.getUserCode(), user.getLoginId(), user.getName(),
                    user.getBirth(), user.getTelNum(), user.getPassword(), user.getPwAnswer(), user.getUserType()
            );

            switch (choice) {
                case "1":
                    System.out.print("현재 비밀번호를 입력하세요 (본인 확인): ");
                    String currentPw = sc.nextLine();
                    if (!currentPw.equals(user.getPassword())) {
                        System.out.println("🚨 비밀번호가 일치하지 않습니다.");
                        continue;
                    }
                    System.out.print("새 비밀번호 입력: ");
                    updatedUser.setPassword(sc.nextLine());
                    break;
                case "2":
                    System.out.print("새 전화번호 입력: ");
                    updatedUser.setTelNum(sc.nextLine());
                    break;
                case "3":
                    System.out.print("새 이름 입력: ");
                    updatedUser.setName(sc.nextLine());
                    break;
                case "4":
                    System.out.print("새 비밀번호 힌트 답 입력: ");
                    updatedUser.setPwAnswer(sc.nextLine());
                    break;
                default:
                    System.out.println("🚨 잘못된 입력입니다.");
                    continue;
            }

            // Controller로 수정된 DTO 전달
            boolean isSuccess = controller.updateUser(updatedUser);

            if (isSuccess) {
                System.out.println("✅ 정보가 성공적으로 수정되었습니다.");
                // 💡 핵심: DB가 수정되었으므로, 현재 접속 중인 세션의 유저 정보도 갱신해야 메인 메뉴에 즉시 반영됩니다.
                SessionManager.getInstance().setLoggedInUser(updatedUser);
                user = updatedUser; // 현재 View가 바라보는 user 객체도 업데이트
            } else {
                System.out.println("🚨 정보 수정에 실패했습니다.");
            }
        }
    }





















}
