package com.module1.crud.global.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadQueries("queries-users.xml");
        loadQueries("queries-grade.xml");
        loadQueries("queries-attendance.xml");
        loadQueries("queries-assignment.xml");
        loadQueries("queries-course.xml");
    }

    private static void loadQueries(String fileName) {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new RuntimeException(fileName + " 파일을 찾을 수 없습니다.");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("query");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);
                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();

                if (queries.containsKey(key)) {
                    throw new RuntimeException("중복된 query key 발견: " + key);
                }

                queries.put(key, sql);
            }
        } catch (Exception e) {
            throw new RuntimeException(fileName + " 쿼리 로딩 중 오류 발생", e);
        }
    }

    public static String getQuery(String key) {
        String query = queries.get(key);

        if (query == null) {
            throw new RuntimeException("해당 key의 쿼리를 찾을 수 없습니다: " + key);
        }

        return query;
    }
}