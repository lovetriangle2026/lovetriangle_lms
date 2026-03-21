package com.module1.crud.users.view;
import com.module1.crud.auth.find.view.FindAccountView;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.dto.UsersDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class UsersInputView {


    private final UsersController controller;
    private final UsersOutputView outputView;
    private final FindAccountView findAccountView;
    private final Scanner sc = new Scanner(System.in);




    public UsersInputView(UsersController usersController, UsersOutputView outputView, FindAccountView findAccountView) {
        this.controller = usersController;
        this.outputView = outputView;
        this.findAccountView = findAccountView; // 주입 완료
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
                    // 💡 주입받은 findAccountView의 메서드 호출
                    // 현재 로그인된 유저의 ID를 넘겨주어 본인 확인 후 비밀번호를 변경하게 함
                    boolean isChanged = findAccountView.loggedResetPassword(user.getLoginId());

                    if (isChanged) {
                        // 성공 시: DB는 이미 수정되었으므로 세션 정보 최신화
                        // 세션 매니저를 통해 DB의 최신 데이터를 다시 로드
                        UsersDTO latestUser = controller.getUserInfo(user.getLoginId());
                        SessionManager.getInstance().setLoggedInUser(latestUser);
                        user = latestUser; // 현재 View의 user 객체도 동기화

                        System.out.println("✅ 회원 정보가 최신화되었습니다.");
                    }
                    // 비밀번호는 find 모듈에서 직접 처리했으므로 switch 아래의 updateUser를 탈 필요 없음
                    continue;
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
