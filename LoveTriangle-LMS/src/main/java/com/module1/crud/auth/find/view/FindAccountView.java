package com.module1.crud.auth.find.view;

import com.module1.crud.auth.find.controller.FindAccountController;
import com.module1.crud.global.utils.InputUtil;
import java.util.Scanner;

public class FindAccountView {
    private final Scanner sc = new Scanner(System.in);
    private final FindAccountController controller;

    public FindAccountView(FindAccountController controller) {
        this.controller = controller;
    }

    // 1. 아이디/비밀번호 찾기 메인 메뉴
    public void showFindMenu() {
        while (true) {
            System.out.println("\n========= [아이디/비밀번호 찾기] =========");
            System.out.println(" 1. 아이디 찾기");
            System.out.println(" 2. 비밀번호 재설정");
            System.out.println(" 0. 메인 메뉴로 돌아가기");
            System.out.println("=========================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": findLoginId(); break;
                case "2": resetPassword(); break;
                case "0": return; // SystemRouter로 즉시 복귀
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 2. 아이디 찾기
    private void findLoginId() {
        System.out.println("\n[아이디 찾기] (이전 메뉴로 돌아가려면 '0'을 입력하세요)");

        // 💡 첫 입력에서 0 체크 (정규식에 |^0$ 추가)
        String userCode = InputUtil.getValueWithRegex(sc,
                "▶ 사번/학번(User Code) 입력: ", "^[A-Za-z]\\d{6}$|^0$", "형식을 확인하거나 뒤로 가려면 0을 입력하세요.");

        if ("0".equals(userCode)) return; // 탈출!

        String name = InputUtil.getValueWithRegex(sc,
                "▶ 이름 입력: ", "^[가-힣A-Za-z]+$", "이름은 한글 또는 영문만 가능합니다.");

        String foundId = controller.findLoginId(userCode, name);

        if (foundId != null) {
            System.out.println("\n🎉 회원님의 아이디는 [" + foundId + "] 입니다.");
        } else {
            System.out.println("\n🚨 일치하는 계정을 찾을 수 없습니다.");
        }
    }

    // 3. 비밀번호 재설정 (비로그인 상태)
    public void resetPassword() {
        System.out.println("\n[비밀번호 재설정] (이전 메뉴로 돌아가려면 '0'을 입력하세요)");

        // 💡 첫 입력에서 0 체크
        String loginId = InputUtil.getValueWithRegex(sc,
                "▶ 로그인 ID 입력: ", "^[A-Za-z0-9]+$|^0$", "ID를 확인하거나 뒤로 가려면 0을 입력하세요.");

        if ("0".equals(loginId)) return; // 탈출!

        String userCode = InputUtil.getValueWithRegex(sc, "▶ 사번/학번 입력: ", "^[A-Za-z]\\d{6}$", "형식이 올바르지 않습니다.");

        System.out.print("▶ [보안 질문] 나의 보물 1호는? 정답 입력: ");
        String answer = sc.nextLine().trim();

        if (controller.verifyPasswordHint(loginId, userCode, answer)) {
            executePasswordReset(loginId);
        } else {
            System.out.println("\n🚨 정보가 일치하지 않습니다.");
        }
    }

    /**
     * 로그인된 유저를 위한 비밀번호 변경 (내 정보 수정 메뉴용)
     */
    /**
     * [완성본] 로그인된 유저를 위한 비밀번호 변경 (내 정보 수정 메뉴용)
     */
    public boolean loggedResetPassword(String loginId) {
        System.out.println("\n🌿 [비밀번호 변경] 🌿");
        System.out.println("(취소하려면 '0'을 입력하세요)");

        System.out.print("▶ 현재 비밀번호 입력: ");
        String currentPw = sc.nextLine().trim();

        // 💡 0번 탈출 로직
        if ("0".equals(currentPw)) {
            System.out.println("취소되었습니다.");
            return false;
        }

        // 💡 1. 현재 비밀번호 검증 (Controller -> Service -> DAO -> BCrypt 체크)
        if (!controller.verifyCurrentPassword(loginId, currentPw)) {
            System.out.println("🚨 현재 비밀번호가 일치하지 않습니다.");
            return false;
        }

        // 💡 2. 검증 통과 시 새 비밀번호 입력 로직으로 이동
        return executePasswordReset(loginId);
    }

    /**
     * 실제 새 비밀번호를 입력받고 저장하는 공통 로직
     */
    private boolean executePasswordReset(String loginId) {
        System.out.println("\n✅ 본인 인증 완료! 새로운 비밀번호를 설정합니다.");
        System.out.println("(취소하려면 '0'을 입력하세요)");

        String newPassword = InputUtil.getValueWithRegex(sc,
                "▶ 새 비밀번호 (영문+숫자+특수문자 8자 이상): ",
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{8,}$|^0$",
                "형식에 맞춰 입력하거나 0을 입력하세요.");

        if ("0".equals(newPassword)) return false;

        System.out.print("▶ 새 비밀번호 확인 입력: ");
        String confirmPw = sc.nextLine().trim();

        if (!newPassword.equals(confirmPw)) {
            System.out.println("🚨 비밀번호가 일치하지 않습니다.");
            return false;
        }

        return controller.resetPassword(loginId, newPassword);
    }
}