package com.module1.crud;// 패키지명은 현재 프로젝트 구조에 맞게 유지해주세요. (예: package com.module1.crud;)

import com.module1.crud.global.config.JDBCTemplate;
// 💡 기존 LoginInputView 임포트를 지우고, SystemRouter를 임포트합니다.
import com.module1.crud.main.SystemRouter;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try (Connection con = JDBCTemplate.getConnection()) {

            // 💡 1. AppConfig에게 조립을 맡기고, 새로 만든 'SystemRouter'를 받습니다.
            SystemRouter systemRouter = AppConfig.createSystemRouter(con);

            // 💡 2. 라우터의 최초 진입점인 start() 메서드를 호출하여 시스템을 가동합니다.
            systemRouter.start();

        } catch (SQLException e) {
            System.err.println("🚨 시스템 오류: " + e.getMessage());
        } finally {
            JDBCTemplate.close();
        }
    }
}
