package com.gradtrack.repository;

import com.gradtrack.model.Interview;
import com.gradtrack.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository <Interview,Long> {
    List<Interview>findByJobApplicationId(Long jobApplicationId);


    List<Interview> findByJobApplication(JobApplication application);
}
