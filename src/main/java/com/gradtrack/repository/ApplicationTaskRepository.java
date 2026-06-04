package com.gradtrack.repository;

import com.gradtrack.model.ApplicationTask;
import com.gradtrack.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationTaskRepository extends JpaRepository <ApplicationTask,Long> {

    List<ApplicationTask> findByJobApplicationIdOrderByDueDateAsc(Long applicationId);

    long countByStatus(TaskStatus status);

    long countByDueDate(LocalDate dueDate);
    long countByDueDateAndStatusNot(LocalDate dueDate, TaskStatus status);


    long countByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    long countByDueDateBetweenAndStatusNot(LocalDate startDate, LocalDate endDate, TaskStatus status);


}
