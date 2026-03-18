package com.module1.crud.assignments.model.dao;

import java.sql.Connection;

public class AssignmentSubmissionDAO {

    private final Connection connection;

    public AssignmentSubmissionDAO(Connection connection) {
        this.connection = connection;
    }
}
