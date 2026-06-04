package com.gradtrack.repository;

import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    List<JobApplication> findByStatus(ApplicationStatus status);

    List<JobApplication> findByCompanyNameContainingIgnoreCaseOrRoleTitleContainingIgnoreCaseOrLocationContainingIgnoreCaseOrNotesContainingIgnoreCase(
            String companyName,
            String roleTitle,
            String location,
            String notes
    );

    long countByStatus (ApplicationStatus status);

    List <JobApplication> findByDeadlineBetween(LocalDate startDate , LocalDate endDate);

}
