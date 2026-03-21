package com.module1.crud.auth.login.view;
import com.module1.crud.auth.login.controller.LoginController;

import java.util.Scanner;

public class LoginView {
    private final Scanner sc = new Scanner(System.in);
    private final LoginController controller;
    private final LoginOutputView outputView;

    // 💡 AppConfig에서 주입받을 컨트롤러와 출력 뷰
    public LoginView(LoginController controller, LoginOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    /**
     * 로그인 화면을 띄우고 인증을 시도합니다.
     * @return 로그인 성공 시 true, 취소 시 false
     */
    public boolean showLoginMenu() {
        while (true) {
            System.out.println("\n--------- [로그인] ---------");
            System.out.println("(이전 메뉴로 돌아가려면 '0'을 입력하세요)");

            System.out.print("ID: ");
            String id = sc.nextLine().trim();

            // 탈출 로직: ID 입력 단계에서 0을 누르면 종료
            if ("0".equals(id)) {
                System.out.println("초기 메뉴로 돌아갑니다.");
                return false; // 로그인 취소 -> SystemRouter로 false 반환
            }

            System.out.print("PW: ");
            String pw = sc.nextLine().trim();

            // 1. Controller를 통해 실제 DB 로그인 수행 (내부에서 BCrypt 검증 완료)
            boolean loginSuccess = controller.login(id, pw);

            // 2. 결과 반환
            if (loginSuccess) {
                // 💡 환영 인사와 메뉴 이동(Routing)은 SystemRouter가 알아서 할 것이므로 true만 던져줍니다!
                return true;
            } else {
                // 로그인 실패 시 루프가 돌면서 다시 입력을 받게 됨
                outputView.printError("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
            }
        }
    }
}