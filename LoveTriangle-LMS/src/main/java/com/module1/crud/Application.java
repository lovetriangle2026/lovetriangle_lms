package com.module1.crud;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.service.AssignmentService;
import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.assignments.view.AssignmentOutputView;
import com.module1.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();


            /**
             * 문서화 주석
             * @deprecated 현재 아래에 작성 될 코드는 나중에는 사라지는 코드
             * 객체 조림
             * */

            AssignmentService service = new AssignmentService(con);
            AssignmentController controller = new AssignmentController(service);
            AssignmentOutputView outputView = new AssignmentOutputView();
            StudentAssignmentInputView inputView = new StudentAssignmentInputView(controller, outputView);
            ProfessorAssignmentInputView inputView1 = new ProfessorAssignmentInputView(controller, outputView);


            inputView.displaymainmenu();
//            inputView1.displaymainmenu();





        } catch (SQLException e) {
            System.err.println("🚨 데이터베이스 연결 실패...");
        } finally {
            JDBCTemplate.close();
        }

    }
}
