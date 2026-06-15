package com.gradtrack.repository;

import com.gradtrack.model.ApplicationActivity;
import com.gradtrack.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ApplicationActivityRepository extends JpaRepository <ApplicationActivity, Long> {

    List<ApplicationActivity> findByJobApplicationIdOrderByCreatedAtDesc(Long jobApplicationId);


    List<ApplicationActivity> findByJobApplicationOrderByCreatedAtDesc(JobApplication application);
}
