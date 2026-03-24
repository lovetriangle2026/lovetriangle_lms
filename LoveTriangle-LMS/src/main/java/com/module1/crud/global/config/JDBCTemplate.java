package com.module1.crud.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {

    private static final HikariDataSource datasource;

    static {

        //커넥션 풀 생성
        Properties prop = new Properties();
        try {
            prop.load(JDBCTemplate.class.getClassLoader().getResourceAsStream("db-info.properties"));

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));

            // connection 관련 설정
            config.setMaximumPoolSize(10);  // 최대 10개의 커넥션 관리
            config.setMinimumIdle(5);       // 최소 5개의 커넥션 유지
            config.setMaxLifetime(180000);  // 커넥션을 사용할 수 있는 최대 시간
            // 30분 후 새롭게 생성한다.
            config.setConnectionTimeout(2000);  // 연결 요청이 2초 이상 지연되면
            // 연결 실패로 인식한다.

            datasource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public static void close() {
        if(datasource != null){
            datasource.close();
        }
    }

    // 옵션 : 커넥션 풀 상태 확인 메서드
    public static void printConnectionStatus() {
        HikariPoolMXBean poolMXBean = datasource.getHikariPoolMXBean();
        System.out.println("🔎[HikariCP 커넥션 풀 상태 확인!!!]");
        System.out.println("총 커넥션 수 : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 : " + poolMXBean.getActiveConnections());
        System.out.println("유휴(idle) 커넥션 수 : " + poolMXBean.getIdleConnections());
        System.out.println("================================");
    }


}
