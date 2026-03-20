package com.module1.crud.course.controller;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.service.CourseService;

import java.sql.SQLException;
import java.util.List;

public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    public List<CourseDTO> findAllCourses() {

        return service.findAllCourses();
    }

    public List<CourseDTO> findMyCourses(int userId) throws SQLException {
        return service.findMyCourses(userId);
    }
    public boolean enrollCourse(int userId, int courseId) {
        return service.enrollCourse(userId, courseId);
    }


}
