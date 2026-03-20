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
import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.service.CourseService;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.course.view.StudentCourseOutputView;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.global.loginpage.model.service.LoginService;
import com.module1.crud.global.loginpage.view.LoginInputView;
import com.module1.crud.global.loginpage.view.LoginOutputView;
import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.service.GradeService;
import com.module1.crud.grade.view.ProfessorGradeInputView;
import com.module1.crud.grade.view.ProfessorGradeOutputView;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.grade.view.StudentGradeOutputView;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.service.UsersService;
import com.module1.crud.users.view.UsersInputView;
import com.module1.crud.users.view.UsersOutputView;


import java.sql.Connection;

public class AppConfig {

    public static LoginInputView createLoginInputView(Connection con) {

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

        // 4. [Grade] - 성적관리 모듈 (객체 공유로 리팩토링)
        GradeService gradeService = new GradeService(con);
        GradeController gradeController = new GradeController(gradeService);
        StudentGradeInputView studentGradeView = new StudentGradeInputView(gradeController, new StudentGradeOutputView());
        ProfessorGradeInputView professorGradeView = new ProfessorGradeInputView(gradeController, new ProfessorGradeOutputView());

        // 5. [Course] - 강의관리 모듈
        CourseService courseService = new CourseService(con);
        CourseController courseController = new CourseController(courseService);
        StudentCourseInputView studentCourseView = new StudentCourseInputView(courseController, new StudentCourseOutputView());

        // 6. [Login] - 로그인 및 최종 조립
        LoginService loginService = new LoginService(con);
        LoginController loginController = new LoginController(loginService);

        // 메인 메뉴 진입점 반환
        return new LoginInputView(
                loginController,
                new LoginOutputView(),
                usersInputView,
                studentAssignmentView,
                professorAssignmentView,
                professorAttendanceView,
                studentAttendanceView,
                studentCourseView,
                studentGradeView,
                professorGradeView
        );
    }
}