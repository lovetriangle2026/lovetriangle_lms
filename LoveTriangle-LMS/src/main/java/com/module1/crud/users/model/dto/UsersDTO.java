package com.module1.crud.users.model.dto;

import java.time.LocalDate;

public class UsersDTO {

    private int id;              // PK (AUTO_INCREMENT)
    private String userCode;     // 유저 식별 코드 (학번/교번 등)
    private String loginId;      // 로그인용 아이디
    private String name;         // 이름
    private LocalDate birth;     // 생년월일 (DB의 DATE와 매핑)
    private String telNum;       // 전화번호
    private String password;     // 비밀번호 (암호화 대비 VARCHAR(255))
    private String pwAnswer;     // 비밀번호 찾기 질문 답변
    private String userType;     // 유저 타입 (학생/교수 등)
    private int isHeartPublic; // 0: 비공개, 1: 공개 (추가!)

    public UsersDTO(int id, String userCode, String loginId, String name, LocalDate birth, String telNum, String password, String pwAnswer, String userType, int isHeartPublic) {
        this.id = id;
        this.userCode = userCode;
        this.loginId = loginId;
        this.name = name;
        this.birth = birth;
        this.telNum = telNum;
        this.password = password;
        this.pwAnswer = pwAnswer;
        this.userType = userType;
        this.isHeartPublic = isHeartPublic;
    }

    public int getIsHeartPublic() {
        return isHeartPublic;
    }

    public void setIsHeartPublic(int isHeartPublic) {
        this.isHeartPublic = isHeartPublic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwAnswer() {
        return pwAnswer;
    }

    public void setPwAnswer(String pwAnswer) {
        this.pwAnswer = pwAnswer;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", loginId='" + loginId + '\'' +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", telNum='" + telNum + '\'' +
                ", password='" + password + '\'' +
                ", pwAnswer='" + pwAnswer + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
