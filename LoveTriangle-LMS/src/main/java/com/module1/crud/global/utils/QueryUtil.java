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

    // XML에서 쿼리를 로드하는 정적 블록
    static {
        loadQueries();
    }

    // XML 파일에서 쿼리를 읽어오는 메서드
    private static void loadQueries() {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml");

            if (inputStream == null) {
                throw new RuntimeException("🚨 queries.xml 파일을 찾을 수 없습니다. 🚨");
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

                queries.put(key, sql);
            }

        } catch (Exception e) {
            throw new RuntimeException("🚨 쿼리 로딩 중 오류 발생 🚨", e);
        }
    }

    // 특정 key로 SQL 가져오기
    public static String getQuery(String key) {
        return queries.get(key);
    }
}