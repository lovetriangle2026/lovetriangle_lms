package com.module1.crud;

import com.module1.crud.attendance.controller.AttendanceController;
import com.module1.crud.attendance.model.service.AttendanceService;
import com.module1.crud.attendance.view.AttendanceOutputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!");
            JDBCTemplate.printConnectionStatus();

            // 객체 조립
            AttendanceService service = new AttendanceService(con);
            AttendanceController controller = new AttendanceController(service);
            AttendanceOutputView outputView = new AttendanceOutputView();

            ProfessorAttendanceInputView professorView =
                    new ProfessorAttendanceInputView(controller, outputView);

            StudentAttendanceInputView studentView =
                    new StudentAttendanceInputView(controller, outputView);

            while (true) {
                System.out.println();
                System.out.println("=================================");
                System.out.println("      출결관리 테스트 메인 메뉴");
                System.out.println("=================================");
                System.out.println("1. 교수 출결관리 페이지");
                System.out.println("2. 학생 출결관리 페이지");
                System.out.println("0. 프로그램 종료");
                System.out.print("번호를 입력해주세요 : ");

                String menu = sc.nextLine();

                switch (menu) {
                    case "1":
                        professorView.displayMenu();
                        break;
                    case "2":
                        studentView.displayMenu();
                        break;
                    case "0":
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("다시 선택해주세요.");
                }
            }

        } catch (SQLException e) {
            System.out.println("🚨 데이터베이스 연결 실패...");
            e.printStackTrace();
        } finally {
            JDBCTemplate.close();
        }
    }
}