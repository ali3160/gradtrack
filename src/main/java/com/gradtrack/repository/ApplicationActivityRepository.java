package com.gradtrack.repository;

import com.gradtrack.model.ApplicationActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationActivityRepository extends JpaRepository <ApplicationActivity, Long> {

    List<ApplicationActivity> findByJobApplicationIdOrderByCreatedAtDesc(Long jobApplicationId);


}
