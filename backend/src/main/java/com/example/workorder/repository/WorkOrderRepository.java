package com.example.workorder.repository;

import com.example.workorder.model.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides database access for work orders.
 *
 * JpaRepository already gives us common operations such as:
 * save, findAll, findById, and delete.
 */
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
}