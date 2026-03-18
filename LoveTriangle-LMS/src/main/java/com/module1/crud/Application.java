package com.module1.crud;

import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.global.loginpage.model.service.LoginService;
import com.module1.crud.global.loginpage.view.LoginInputView;
import com.module1.crud.global.loginpage.view.LoginOutputView;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.service.UsersService;
import com.module1.crud.users.view.UsersInputView;
import com.module1.crud.users.view.UsersOutputView;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            //users 의존성주입
            UsersService usersService = new UsersService(con);
            UsersController usersController = new UsersController(usersService);
            UsersOutputView usersOutputView = new UsersOutputView();
            UsersInputView usersInputView = new UsersInputView(usersController, usersOutputView);




            //Loginpage 의존성 주입
            LoginService loginService = new LoginService(con);
            LoginController loginController = new LoginController(loginService);
            LoginOutputView loginOutputView = new LoginOutputView();
            LoginInputView loginInputView = new LoginInputView(loginController, loginOutputView, usersInputView);

            loginInputView.displayStartMenu();

        } catch (SQLException e) {
            System.err.println("🚨 데이터베이스 연결 실패...");
        } finally {
            JDBCTemplate.close();
        }


    }

}
