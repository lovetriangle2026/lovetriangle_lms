package com.module1.crud.course.controller;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.CourseStudentStatsDTO;
import com.module1.crud.course.model.service.CourseService;
import com.module1.crud.course.view.ProfInputView;
import com.module1.crud.course.view.ProfOutputView;

import java.sql.SQLException;
import java.util.List;

public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }


    public List<CourseDTO> findAllCourses() {
        // 서비스에서 데이터만 가져와서 그대로 넘겨줍니다.
        return service.findAllCourses();
    }



    public List<CourseDTO> findMyCourses(int userId) {
        return service.findMyCourses(userId);
    }
    public boolean enrollCourse(int userId, int courseId) {
        return service.enrollCourse(userId, courseId);
    }


    public List<CourseDTO> findProfessorCourses(int professorId) {
        return service.findProfessorCourses(professorId);
    }


    public boolean insertCourse(CourseDTO newCourse) {
        return service.insertCourse(newCourse);
    }

    public List<CourseStudentStatsDTO> findStudentStatsByCourse(int courseId) {
        return service.findStudentStatsByCourse(courseId);
    }
}
