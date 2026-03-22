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
import com.module1.crud.auth.find.controller.FindAccountController;
import com.module1.crud.auth.find.service.FindAccountService;
import com.module1.crud.auth.find.view.FindAccountView;
import com.module1.crud.auth.login.view.LoginView;
import com.module1.crud.auth.signup.controller.SignupController;
import com.module1.crud.auth.signup.service.SignupService;
import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.controller.SessionController;
import com.module1.crud.course.model.service.CourseService;
import com.module1.crud.course.model.service.SessionService;
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

public class AppConfig {

    // 💡 더 이상 Connection 파라미터가 필요 없습니다!
    public static SystemRouter createSystemRouter() {
        return new SystemRouter();
    }
    // =========================================================
    // 💡 [On-Demand Factory Methods] 필요할 때마다 즉석에서 객체 조립!
    // =========================================================

    public static LoginView createLoginView() {
        return new LoginView(new LoginController(new LoginService()), new LoginOutputView());
    }

    public static SignupView createSignupView() {
        return new SignupView(new SignupController(new SignupService()));
    }

    public static FindAccountView createFindAccountView() {
        return new FindAccountView(new FindAccountController(new FindAccountService()));
    }

    public static UsersInputView createUsersInputView() {
        // UsersInputView 생성자에 findAccountView가 필요하므로 공장 메서드 재사용!
        return new UsersInputView(new UsersController(new UsersService()), new UsersOutputView(), createFindAccountView());
    }

    // ================== 학생 전용 뷰 생성 ==================
    public static StudentCourseInputView createStudentCourseInputView() {

        SessionController sessionController =
                new SessionController(new SessionService());

        return new StudentCourseInputView(
                new CourseController(new CourseService()),
                new StudentCourseOutputView(),
                sessionController
        );
    }

    public static StudentAttendanceInputView createStudentAttendanceInputView() {
        return new StudentAttendanceInputView(new AttendanceController(new AttendanceService()), new AttendanceOutputView());
    }

    public static StudentGradeInputView createStudentGradeInputView() {
        return new StudentGradeInputView(new GradeController(new GradeService()), new StudentGradeOutputView());
    }

    public static StudentAssignmentInputView createStudentAssignmentInputView() {
        return new StudentAssignmentInputView(new AssignmentController(new AssignmentService()), new AssignmentOutputView());
    }

    // ================== 교수 전용 뷰 생성 ==================
    public static ProfInputView createProfInputView() {
        return new ProfInputView(
                new CourseController(new CourseService()),
                new ProfOutputView(),
                new SessionController(new SessionService())
        );
    }

    public static ProfessorAttendanceInputView createProfessorAttendanceInputView() {
        return new ProfessorAttendanceInputView(new AttendanceController(new AttendanceService()), new AttendanceOutputView());
    }

    public static ProfessorGradeInputView createProfessorGradeInputView() {
        return new ProfessorGradeInputView(new GradeController(new GradeService()), new ProfessorGradeOutputView());
    }

    public static ProfessorAssignmentInputView createProfessorAssignmentInputView() {
        return new ProfessorAssignmentInputView(new AssignmentController(new AssignmentService()), new AssignmentOutputView());


    }
}