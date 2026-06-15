package com.gradtrack.repository;

import com.gradtrack.model.AppUser;
import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


    List <JobApplication> findNyAppUser (AppUser appUser);
    List <JobApplication> findByAppUserAndStatus(AppUser appUser, ApplicationStatus status);

    List<JobApplication> findByAppUserAndRoleTitleContainingIgnoreCaseOrAppUserAndCompanyNameContainingIgnoreCase(
            AppUser appUser1,
            String roleTitle,
            AppUser appUser2,
            String companyName
    );
    Long countByAppUser(AppUser appUser);
    long countByAppUserAndStatus(AppUser appUser, ApplicationStatus status);

    List<JobApplication> findByAppUserAndDeadlineBetween(
            AppUser appUser,
            LocalDate start,
            LocalDate end
    );


    List<JobApplication> findByAppUser(AppUser appUser);

    @Query("""
            SELECT j FROM JobApplication j
            WHERE j.appUser = :appUser
            AND (
                LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(j.roleTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(j.notes) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    List<JobApplication> searchByKeywordForUser(AppUser appUser, String keyword);}
