package com.example.workorder.controller;

import com.example.workorder.dto.CreateWorkOrderRequest;
import com.example.workorder.dto.UpdateWorkOrderRequest;
import com.example.workorder.model.WorkOrder;
import com.example.workorder.service.WorkOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API used by the frontend to create, list, and update work orders.
 */
@RestController
@RequestMapping("/api/work-orders")

// During local development the React/Vite frontend runs on port 5173.
@CrossOrigin(origins = "http://localhost:5173")
public class WorkOrderController {

    private final WorkOrderService service;

    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    /**
     * GET /api/work-orders
     *
     * Returns all work orders.
     */
    @GetMapping
    public List<WorkOrder> getAll() {
        return service.getAll();
    }

    /**
     * POST /api/work-orders
     *
     * Creates a work order.
     * @Valid causes the validation rules in CreateWorkOrderRequest
     * to run before the service is called.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrder create(
            @Valid @RequestBody CreateWorkOrderRequest request) {

        return service.create(request);
    }

    /**
     * PUT /api/work-orders/{id}
     *
     * Updates an existing work order.
     */
    @PutMapping("/{id}")
    public WorkOrder update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkOrderRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}