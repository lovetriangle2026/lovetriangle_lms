package com.module1.crud.auth.find.view;

import com.module1.crud.auth.find.controller.FindAccountController;
import com.module1.crud.global.utils.InputUtil;

import java.util.Scanner;

public class FindAccountView {
    private final Scanner sc = new Scanner(System.in);
    private final FindAccountController controller;

    // 💡 생성자를 통해 Controller 주입
    public FindAccountView(FindAccountController controller) {
        this.controller = controller;
    }

    // 1. 아이디/비밀번호 찾기 메인 메뉴
    public void showFindMenu() {
        while (true) {
            System.out.println("\n========= [아이디/비밀번호 찾기] =========");
            System.out.println("1. 아이디 찾기");
            System.out.println("2. 비밀번호 재설정 (힌트 질문 인증)");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    findLoginId();
                    break;
                case "2":
                    resetPassword();
                    break;
                case "0":
                    return; // SystemRouter로 돌아감
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 2. 아이디 찾기 로직
    private void findLoginId() {
        System.out.println("\n[아이디 찾기] 가입 시 입력한 정보를 입력해주세요.");

        // 💡 InputUtil을 활용한 우아하고 짧은 입력 로직!
        String userCode = InputUtil.getValueWithRegex(sc,
                "사번/학번(User Code) 입력 : ", "^[A-Za-z]\\d{6}$", "영문자 1자리 + 숫자 6자리로 입력해주세요.");
        String name = InputUtil.getValueWithRegex(sc,
                "이름 입력 : ", "^[가-힣A-Za-z]+$", "이름은 한글 또는 영문만 입력 가능합니다.");

        // Controller에게 아이디를 찾아달라고 요청 (아직 미구현)
        String foundId = controller.findLoginId(userCode, name);

        if (foundId != null) {
            System.out.println("\n🎉 회원님의 아이디는 [" + foundId + "] 입니다.");
        } else {
            System.out.println("\n🚨 입력하신 정보와 일치하는 계정을 찾을 수 없습니다.");
        }
    }

    // 3. 비밀번호 재설정 로직
    private void resetPassword() {
        System.out.println("\n[비밀번호 재설정] 계정 확인 및 본인 인증을 진행합니다.");

        String loginId = InputUtil.getValueWithRegex(sc,
                "로그인 ID 입력 : ", "^[A-Za-z0-9]+$", "ID는 영문과 숫자만 사용할 수 있습니다.");
        String userCode = InputUtil.getValueWithRegex(sc,
                "사번/학번(User Code) 입력 : ", "^[A-Za-z]\\d{6}$", "영문자 1자리 + 숫자 6자리로 입력해주세요.");

        System.out.println("\n[보안 인증] 나의 보물 1호는?");
        String answer = InputUtil.getValueWithRegex(sc,
                "정답 입력 : ", "^.{1,30}$", "1자 이상 30자 이내로 입력해주세요.");

        // 1차: 입력한 정보(ID, 학번, 답변)가 일치하는지 검증 (아직 미구현)
        boolean isVerified = controller.verifyPasswordHint(loginId, userCode, answer);

        if (isVerified) {
            System.out.println("\n✅ 본인 인증이 완료되었습니다. 새로운 비밀번호를 설정해주세요.");

            // 2차: 새로운 비밀번호 입력받기
            String newPassword = InputUtil.getValueWithRegex(sc,
                    "새 비밀번호 (영문, 숫자, 특수문자) : ",
                    "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$",
                    "비밀번호는 영문, 숫자, 특수문자만 포함해야 합니다.");

            // 3차: 새로운 비밀번호로 DB 업데이트 요청 (아직 미구현)
            boolean isResetSuccess = controller.resetPassword(loginId, newPassword);

            if (isResetSuccess) {
                System.out.println("🎉 비밀번호가 성공적으로 변경되었습니다! 변경된 비밀번호로 로그인해주세요.");
            } else {
                System.out.println("🚨 비밀번호 변경 중 오류가 발생했습니다.");
            }
        } else {
            System.out.println("\n🚨 정보가 일치하지 않습니다. 아이디, 학번, 혹은 답변을 다시 확인해주세요.");
        }
    }
}