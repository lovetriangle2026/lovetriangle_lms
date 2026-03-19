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
import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.global.loginpage.model.service.LoginService;
import com.module1.crud.global.loginpage.view.LoginInputView;
import com.module1.crud.global.loginpage.view.LoginOutputView;
import com.module1.crud.grade.controller.GradeController;
import com.module1.crud.grade.model.service.GradeService;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.grade.view.StudentGradeOutputView;
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

            //Assiginment 의존성주입
            AssignmentService service = new AssignmentService(con);
            AssignmentController controller = new AssignmentController(service);
            AssignmentOutputView outputView = new AssignmentOutputView();
            StudentAssignmentInputView inputView = new StudentAssignmentInputView(controller, outputView);
            ProfessorAssignmentInputView inputView1 = new ProfessorAssignmentInputView(controller, outputView);
          
            // Attendance 의존성 주입
            AttendanceService attendanceService = new AttendanceService(con);
            AttendanceController attendanceController = new AttendanceController(attendanceService);
            AttendanceOutputView attendanceOutputView = new AttendanceOutputView();
            ProfessorAttendanceInputView professorView = new ProfessorAttendanceInputView(attendanceController, attendanceOutputView);
            StudentAttendanceInputView studentView =new StudentAttendanceInputView(attendanceController, attendanceOutputView);

            GradeService gradeService = new GradeService(con);
            GradeController gradeController = new GradeController(gradeService);
            StudentGradeOutputView studentGradeOutputView = new StudentGradeOutputView();
            StudentGradeInputView studentGradeInputView = new StudentGradeInputView(gradeController,studentGradeOutputView);




            //course 의존성 주입
            // 1. Service 생성 (DB 연결 객체인 con을 넣어줍니다)
            CourseService courseService = new CourseService(con);
// 2. Controller 생성 (방금 만든 service를 넣어줍니다)
            CourseController courseController = new CourseController(courseService);
// 3. OutputView 생성 (데이터를 보여줄 도구)
            StudentCourseOutputView studentCourseOutputView = new StudentCourseOutputView();
// 4. InputView 생성 (사용자 입력을 받고, 컨트롤러와 출력뷰를 연결)
            StudentCourseInputView studentCourseInputView = new StudentCourseInputView(courseController, studentCourseOutputView);



            //Loginpage 의존성 주입
            LoginService loginService = new LoginService(con);
            LoginController loginController = new LoginController(loginService);
            LoginOutputView loginOutputView = new LoginOutputView();
            LoginInputView loginInputView = new LoginInputView(loginController, loginOutputView, usersInputView, inputView, inputView1, professorView, studentView, studentCourseInputView,studentGradeInputView);
           

            loginInputView.displayStartMenu();

        } catch (SQLException e) {
            System.err.println("🚨 데이터베이스 연결 실패...");
        } finally {
            JDBCTemplate.close();
        }


    }

}
