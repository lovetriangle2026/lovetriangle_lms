package com.module1.crud;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.service.AssignmentService;
import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.assignments.view.AssignmentOutputView;
import com.module1.crud.attendance.controller.AttendanceController;
import com.module1.crud.attendance.model.service.AttendanceService;
import com.module1.crud.attendance.view.AttendanceOutputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.auth.find.controller.FindAccountController;
import com.module1.crud.auth.find.service.FindAccountService;
import com.module1.crud.auth.find.view.FindAccountView;
import com.module1.crud.auth.login.view.LoginView;
import com.module1.crud.auth.signup.controller.SignupController;
import com.module1.crud.auth.signup.service.SignupService;
import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.service.CourseService;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.course.view.StudentCourseOutputView;

// 💡 새롭게 분리할 auth 패키지 경로를 미리 임포트 (빨간 줄이 뜨는 것이 정상입니다)

import com.module1.crud.auth.signup.view.SignupView;
import com.module1.crud.auth.login.controller.LoginController;
import com.module1.crud.auth.login.service.LoginService;
import com.module1.crud.auth.login.view.LoginOutputView;

import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.service.GradeService;
import com.module1.crud.grade.view.ProfessorGradeInputView;
import com.module1.crud.grade.view.ProfessorGradeOutputView;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.grade.view.StudentGradeOutputView;
import com.module1.crud.main.SystemRouter;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.service.UsersService;
import com.module1.crud.users.view.UsersInputView;
import com.module1.crud.users.view.UsersOutputView;

import java.sql.Connection;

public class AppConfig {

    // 💡 1. 반환 타입과 메서드 이름을 SystemRouter에 맞게 변경합니다.
    public static SystemRouter createSystemRouter(Connection con) {

        // 1. [Users] - 회원관리 모듈
        UsersService usersService = new UsersService(con);
        UsersController usersController = new UsersController(usersService);
        UsersInputView usersInputView = new UsersInputView(usersController, new UsersOutputView());

        // 2. [Assignment] - 과제관리 모듈
        AssignmentService assignmentService = new AssignmentService(con);
        AssignmentController assignmentController = new AssignmentController(assignmentService);
        AssignmentOutputView assignmentOutputView = new AssignmentOutputView();
        StudentAssignmentInputView studentAssignmentView = new StudentAssignmentInputView(assignmentController, assignmentOutputView);
        ProfessorAssignmentInputView professorAssignmentView = new ProfessorAssignmentInputView(assignmentController, assignmentOutputView);

        // 3. [Attendance] - 출결관리 모듈
        AttendanceService attendanceService = new AttendanceService(con);
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        AttendanceOutputView attendanceOutputView = new AttendanceOutputView();
        ProfessorAttendanceInputView professorAttendanceView = new ProfessorAttendanceInputView(attendanceController, attendanceOutputView);
        StudentAttendanceInputView studentAttendanceView = new StudentAttendanceInputView(attendanceController, attendanceOutputView);

        // 4. [Grade] - 성적관리 모듈
        GradeService gradeService = new GradeService(con);
        GradeController gradeController = new GradeController(gradeService);
        StudentGradeInputView studentGradeView = new StudentGradeInputView(gradeController, new StudentGradeOutputView());
        ProfessorGradeInputView professorGradeView = new ProfessorGradeInputView(gradeController, new ProfessorGradeOutputView());

        // 5. [Course] - 강의관리 모듈
        CourseService courseService = new CourseService(con);
        CourseController courseController = new CourseController(courseService);
        StudentCourseInputView studentCourseView = new StudentCourseInputView(courseController, new StudentCourseOutputView());

        AuthDAO authDAO = new AuthDAO(con);

        // 2. 각각의 Service에 동일한 AuthDAO를 꽂아줌
        LoginService loginService = new LoginService(authDAO);
        SignupService signupService = new SignupService(authDAO);
        FindAccountService findAccountService = new FindAccountService(authDAO);

        // 3. 각각의 Controller에 매칭되는 Service를 꽂아줌
        LoginController loginController = new LoginController(loginService);
        SignupController signupController = new SignupController(signupService);
        FindAccountController findAccountController = new FindAccountController(findAccountService);

        // 4. 각각의 View에 매칭되는 Controller를 꽂아줌
        LoginOutputView loginOutputView = new LoginOutputView(); // (기존 에러메시지 출력용 유지)
        LoginView loginView = new LoginView(loginController, loginOutputView);
        SignupView signupView = new SignupView(signupController);
        FindAccountView findAccountView = new FindAccountView(findAccountController);

// 💡 3. 최종 조립: 메인 메뉴 진입점(Router)에 모든 부품을 순서대로 꽂아서 반환합니다.
        return new SystemRouter(
                loginView,
                signupView,
                findAccountView,
                studentCourseView,
                studentAttendanceView,
                professorAttendanceView,
                studentGradeView,
                professorGradeView,
                studentAssignmentView,
                professorAssignmentView, // 👈 추가됨! (AppConfig 상단에서 만든 객체)
                usersInputView
        );
    }
}