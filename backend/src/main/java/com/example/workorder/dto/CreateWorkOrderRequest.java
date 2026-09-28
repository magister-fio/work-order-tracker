package com.example.workorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used when creating a new work order.
 *
 * Keeping request validation separate from the database entity
 * prevents invalid API input from being saved directly.
 */
public class CreateWorkOrderRequest {

    // Every work order must have a title.
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be 200 characters or less")
    private String title;

    // Description is optional, but limited so very large input is rejected.
    @Size(max = 1000, message = "Description must be 1000 characters or less")
    private String description;

    // Assignment is optional.
    @Size(max = 200, message = "Assigned to must be 200 characters or less")
    private String assignedTo;

    // Status is optional during creation.
    // The service defaults it to OPEN when it is missing.
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}