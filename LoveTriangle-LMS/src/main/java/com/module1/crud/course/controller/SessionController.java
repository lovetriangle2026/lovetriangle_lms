package com.module1.crud.course.controller;

import com.module1.crud.course.model.dto.SessionDTO;
import com.module1.crud.course.model.service.SessionService;

import java.util.List;

public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    public boolean updateSessionTitle(int courseId, int week, String title) {
        return service.updateSessionTitle(courseId, week, title);
    }

    public List<SessionDTO> findSessionsByCourse(int courseId) {
        return service.findSessionsByCourse(courseId);
    }
}