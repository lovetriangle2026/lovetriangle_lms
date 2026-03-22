package com.module1.crud.course.controller;

import com.module1.crud.course.model.dto.SessionDTO;
import com.module1.crud.course.model.service.SessionService;
import com.module1.crud.course.view.ProfOutputView;

import java.util.List;

public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    public boolean updateSessionTitle(int courseId, int week, String title) {
        return service.updateSessionTitle(courseId, week, title);
    }

    // ⭐ 중복된 아래쪽 메서드는 지우고, 이 하나만 남겨야 합니다!
    public List<SessionDTO> findSessionsByCourse(int courseId) {
        // 뷰 호출 코드(new ProfOutputView()...)를 완전히 삭제하세요!
        return service.findSessionsByCourse(courseId);
    }
}