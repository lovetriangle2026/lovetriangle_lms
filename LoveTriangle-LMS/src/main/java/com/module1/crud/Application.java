package com.module1.crud;

import com.module1.crud.global.config.JDBCTemplate;

import com.module1.crud.global.loginpage.view.LoginInputView;



import java.sql.Connection;
import java.sql.SQLException;
public class Application {
    public static void main(String[] args) {
        try (Connection con = JDBCTemplate.getConnection()) {

            // AppConfig에게 조립을 맡기고 결과만 받습니다.
            LoginInputView loginInputView = AppConfig.createLoginInputView(con);

            loginInputView.displayStartMenu();

        } catch (SQLException e) {
            System.err.println("🚨 시스템 오류: " + e.getMessage());
        } finally {
            JDBCTemplate.close();
        }
    }
}