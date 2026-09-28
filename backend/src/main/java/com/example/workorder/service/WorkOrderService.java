package com.example.workorder.service;

import com.example.workorder.dto.CreateWorkOrderRequest;
import com.example.workorder.dto.UpdateWorkOrderRequest;
import com.example.workorder.exception.WorkOrderNotFoundException;
import com.example.workorder.model.WorkOrder;
import com.example.workorder.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles the small amount of business logic needed for work orders.
 *
 * The controller handles HTTP concerns, while this class handles
 * creation, lookup, and update behavior.
 */
@Service
public class WorkOrderService {

    private final WorkOrderRepository repository;

    /**
     * Repository is provided by Spring through constructor injection.
     */
    public WorkOrderService(WorkOrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all currently stored work orders.
     */
    public List<WorkOrder> getAll() {
        return repository.findAll();
    }

    /**
     * Creates a new work order from the API request.
     */
    public WorkOrder create(CreateWorkOrderRequest request) {

        WorkOrder workOrder = new WorkOrder();

        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setAssignedTo(request.getAssignedTo());

        // OPEN is used as the default state when the caller does not
        // explicitly provide a status.
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            workOrder.setStatus("OPEN");
        } else {
            workOrder.setStatus(request.getStatus());
        }

        return repository.save(workOrder);
    }

    /**
     * Updates an existing work order.
     *
     * The record is loaded first so an invalid ID does not accidentally
     * create a new work order.
     */
    public WorkOrder update(Long id, UpdateWorkOrderRequest request) {

        WorkOrder existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Work order not found"));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setAssignedTo(request.getAssignedTo());
        existing.setStatus(request.getStatus());

        return repository.save(existing);
    }

    public void delete(Long id) {
        WorkOrder existing = repository.findById(id)
                            .orElseThrow(() -> new WorkOrderNotFoundException(id));
        repository.delete(existing);
    }
}