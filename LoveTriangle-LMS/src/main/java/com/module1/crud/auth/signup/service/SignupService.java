package com.module1.crud.auth.signup.service;

import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.users.model.dto.UsersDTO;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignupService {
    private final AuthDAO authDAO;

    public SignupService(AuthDAO authDAO) { this.authDAO = authDAO; }

    public String verifyUser(String userCode, String name, LocalDate birth, String userType) {
        try {
            if (!authDAO.checkSchoolMember(userCode, name, birth, userType)) return "NOT_FOUND";
            if (authDAO.checkAlreadyRegistered(userCode)) return "ALREADY_REGISTERED";
            return "AVAILABLE";
        } catch (SQLException e) { return "ERROR"; }
    }

    public Long saveUser(UsersDTO newUser) {
        try {
            newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
            return authDAO.save(newUser);
        } catch (SQLException e) { throw new RuntimeException("회원 가입 Error", e); }
    }
}