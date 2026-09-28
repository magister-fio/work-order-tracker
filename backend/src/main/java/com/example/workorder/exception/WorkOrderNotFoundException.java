package com.example.workorder.exception;

public class WorkOrderNotFoundException extends RuntimeException {

    public WorkOrderNotFoundException(Long id) {
        super("Work order not found with id " + id);
    }
}