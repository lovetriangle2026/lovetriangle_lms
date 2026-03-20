package com.module1.crud.course.controller;

import com.module1.crud.course.model.dto.CourseDTO;
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


    public void findAllCourses() { // 반환타입을 void로!
        List<CourseDTO> courseList = service.findAllCourses();
        ProfOutputView profOutputView = new ProfOutputView();
        profOutputView.displayCourseList(courseList); //
    }



    public List<CourseDTO> findMyCourses(int userId) throws SQLException {
        return service.findMyCourses(userId);
    }

    public void registerNewCourse() {
        ProfOutputView profOutputView = new ProfOutputView();
        ProfInputView profInputView = new ProfInputView(this, profOutputView);

        CourseDTO newCourse = profInputView.inputCourse();
        int result = service.insertCourse(newCourse);
        profOutputView.displayResult(result);
    }








}
