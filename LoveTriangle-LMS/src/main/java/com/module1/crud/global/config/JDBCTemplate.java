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

        Properties prop = new Properties();

        try {
            prop.load(JDBCTemplate.class
                    .getClassLoader()
                    .getResourceAsStream("db-info.properties"));

            HikariConfig config = new HikariConfig();

            // ✅ DB 설정
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));

            // ✅ MySQL 드라이버 명시
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // ✅ 커넥션 풀 설정
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(2000);

            datasource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public static void close() {
        if (datasource != null) {
            datasource.close();
        }
    }

    public static void printConnectionStatus() {
        HikariPoolMXBean poolMXBean = datasource.getHikariPoolMXBean();

        System.out.println("[HikariCP 커넥션 풀 상태]");
        System.out.println("총 커넥션 수 : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 : " + poolMXBean.getIdleConnections());
        System.out.println("=================================");
    }
}