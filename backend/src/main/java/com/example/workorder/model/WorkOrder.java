package com.example.workorder.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Database entity representing one work order.
 *
 * This class maps directly to the work_orders table in SQL Server.
 */
@Entity
@Table(name = "work_orders")
public class WorkOrder {

    // Primary key generated automatically by SQL Server.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Short title describing the work that needs to be done.
    @Column(nullable = false, length = 200)
    private String title;

    // Optional longer description of the work order.
    @Column(length = 1000)
    private String description;

    // Current work order state such as OPEN, IN_PROGRESS, or COMPLETED.
    @Column(nullable = false, length = 50)
    private String status;

    // Name of the person currently responsible for the work order.
    @Column(name = "assigned_to", length = 200)
    private String assignedTo;

    // Timestamp showing when the record was first created.
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Timestamp showing when the record was last updated.
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * JPA requires a no-argument constructor.
     */
    public WorkOrder() {
    }

    /**
     * Runs automatically before a new work order is inserted.
     *
     * Both timestamps are initialized here. If no status was supplied,
     * the work order starts as OPEN.
     */
    @PrePersist
    public void beforeCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null || status.isBlank()) {
            status = "OPEN";
        }
    }

    /**
     * Runs automatically before an existing work order is updated.
     */
    @PreUpdate
    public void beforeUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}