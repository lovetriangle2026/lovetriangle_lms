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
import com.module1.crud.course.view.ProfInputView;
import com.module1.crud.course.view.ProfOutputView;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.course.view.StudentCourseOutputView;

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

    public static SystemRouter createSystemRouter(Connection con) {

        // 💡 1. [공통 인프라] - DAO 생성
        AuthDAO authDAO = new AuthDAO(con);

        // 💡 2. [Auth 모듈] - UsersInputView에 주입하기 위해 먼저 생성합니다.
        // 서비스 생성
        LoginService loginService = new LoginService(authDAO);
        SignupService signupService = new SignupService(authDAO);
        FindAccountService findAccountService = new FindAccountService(authDAO);

        // 컨트롤러 생성
        LoginController loginController = new LoginController(loginService);
        SignupController signupController = new SignupController(signupService);
        FindAccountController findAccountController = new FindAccountController(findAccountService);

        // 뷰 생성
        LoginOutputView loginOutputView = new LoginOutputView();
        LoginView loginView = new LoginView(loginController, loginOutputView);
        SignupView signupView = new SignupView(signupController);
        FindAccountView findAccountView = new FindAccountView(findAccountController);


        // 💡 3. [Users 모듈] - 위에서 만든 findAccountView를 생성자 인자로 주입합니다.
        UsersService usersService = new UsersService(con);
        UsersController usersController = new UsersController(usersService);
        // 👈 생성자 마지막에 findAccountView가 추가되어야 합니다 (UsersInputView 생성자 수정 필요)
        UsersInputView usersInputView = new UsersInputView(usersController, new UsersOutputView(), findAccountView);


        // 4. [Assignment] - 과제관리 모듈
        AssignmentService assignmentService = new AssignmentService(con);
        AssignmentController assignmentController = new AssignmentController(assignmentService);
        AssignmentOutputView assignmentOutputView = new AssignmentOutputView();
        StudentAssignmentInputView studentAssignmentView = new StudentAssignmentInputView(assignmentController, assignmentOutputView);
        ProfessorAssignmentInputView professorAssignmentView = new ProfessorAssignmentInputView(assignmentController, assignmentOutputView);

        // 5. [Attendance] - 출결관리 모듈
        AttendanceService attendanceService = new AttendanceService(con);
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        AttendanceOutputView attendanceOutputView = new AttendanceOutputView();
        ProfessorAttendanceInputView professorAttendanceView = new ProfessorAttendanceInputView(attendanceController, attendanceOutputView);
        StudentAttendanceInputView studentAttendanceView = new StudentAttendanceInputView(attendanceController, attendanceOutputView);

        // 6. [Grade] - 성적관리 모듈
        GradeService gradeService = new GradeService(con);
        GradeController gradeController = new GradeController(gradeService);
        StudentGradeInputView studentGradeView = new StudentGradeInputView(gradeController, new StudentGradeOutputView());
        ProfessorGradeInputView professorGradeView = new ProfessorGradeInputView(gradeController, new ProfessorGradeOutputView());

        // 7. [Course] - 강의관리 모듈
        CourseService courseService = new CourseService(con);
        CourseController courseController = new CourseController(courseService);
        StudentCourseInputView studentCourseView = new StudentCourseInputView(courseController, new StudentCourseOutputView());
        ProfInputView profInputView = new ProfInputView(courseController, new ProfOutputView());


        // 💡 8. 최종 조립: 메인 메뉴 진입점(Router)에 모든 부품을 순서대로 꽂아서 반환합니다.
        return new SystemRouter(
                loginView,
                signupView,
                findAccountView,
                studentCourseView,
                profInputView,
                studentAttendanceView,
                professorAttendanceView,
                studentGradeView,
                professorGradeView,
                studentAssignmentView,
                professorAssignmentView,
                usersInputView
        );
    }
}