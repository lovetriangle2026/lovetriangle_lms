package com.module1.crud.global.session;

import com.module1.crud.users.model.dto.UsersDTO;

public class SessionManager {

    // 1. 단 하나의 객체만 유지하기 위한 static 전역 변수
    private static SessionManager instance;

    // 2. 로그인에 성공한 사용자의 정보를 담을 DTO 변수
    private UsersDTO loggedInUser;

    // 3. 💡 핵심: 외부에서 new SessionManager()를 할 수 없도록 생성자를 private으로 막아버림
    private SessionManager() {
    }

    // 4. 오직 이 메서드를 통해서만 SessionManager 객체(사물함)에 접근할 수 있음
    public static SessionManager getInstance() {
        if (instance == null) {
            // 사물함이 없으면 딱 한 번만 만듦
            instance = new SessionManager();
        }
        return instance;
    }

    // ---------------------------------------------------------
    // 아래는 사물함을 조작하는 기능들입니다.


    // 로그인 성공 시 사용자 정보를 사물함에 넣기
    public void setLoggedInUser(UsersDTO user) {
        this.loggedInUser = user;
    }

    // 현재 사물함에 들어있는 사용자 정보 꺼내보기 (누가 로그인했는지 확인)
    public UsersDTO getLoggedInUser() {
        return this.loggedInUser;
    }

    // 로그아웃 (사물함 비우기)
    public void clearSession() {
        this.loggedInUser = null;
    }

    // 현재 누군가 로그인한 상태인지 T/F로 확인
    public boolean isLoggedIn() {
        return this.loggedInUser != null;
    }
}