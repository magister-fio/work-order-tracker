package com.example.workorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used when updating an existing work order.
 *
 * The update endpoint expects the current values for the main fields.
 */
public class UpdateWorkOrderRequest {

    // Title remains required during an update.
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be 200 characters or less")
    private String title;

    @Size(max = 1000, message = "Description must be 1000 characters or less")
    private String description;

    @Size(max = 200, message = "Assigned to must be 200 characters or less")
    private String assignedTo;

    // Status is required when updating the record.
    @NotBlank(message = "Status is required")
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