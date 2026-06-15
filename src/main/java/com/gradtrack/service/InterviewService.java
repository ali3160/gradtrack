package com.gradtrack.service;

import com.gradtrack.dto.InterviewPatchRequest;
import com.gradtrack.dto.InterviewRequest;
import com.gradtrack.dto.InterviewResponse;
import com.gradtrack.exception.InterviewNotFoundException;
import com.gradtrack.model.*;
import com.gradtrack.repository.InterviewRepository;
import com.gradtrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {


    private final InterviewRepository interviewRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final ApplicationActivityService activityService;
    private final JobApplicationService jobApplicationService;


    public InterviewService(InterviewRepository interviewRepository,
                            JobApplicationRepository jobApplicationRepository, ApplicationActivityService activityService, JobApplicationService jobApplicationService) {
        this.interviewRepository = interviewRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.activityService = activityService;
        this.jobApplicationService = jobApplicationService;
    }

    public InterviewResponse createInterview(long applicationId , InterviewRequest request){

        JobApplication application = jobApplicationService.findApplicationOrThrow(applicationId);

        Interview interview = new Interview();
        interview.setJobApplication(application);
        interview.setInterviewDateTime(request.getInterviewDateTime());
        interview.setInterviewType(request.getInterviewType());

        if (request.getOutcome() == null) {
            interview.setOutcome(InterviewOutcome.PENDING);
        } else {
            interview.setOutcome(request.getOutcome());
        }
        interview.setInterviewerName(request.getInterviewerName());
        interview.setNotes(request.getNotes());

        application.setStatus(ApplicationStatus.INTERVIEW);
        jobApplicationRepository.save(application);



        Interview savedInterview = interviewRepository.save(interview);

        activityService.recordActivity(application, ActivityType.INTERVIEW_CREATED,
                request.getInterviewType() + " interview scheduled for " + request.getInterviewDateTime() );

        return mapToResponse(savedInterview);

    }

    public List<InterviewResponse> getInterviewsForApplication(Long applicationId){

        JobApplication application = jobApplicationService.findApplicationOrThrow(applicationId);

        List <Interview> interviews = interviewRepository.findByJobApplication(application);

        return interviews.stream().map(this::mapToResponse).toList();

    }

    public InterviewResponse getInterviewById(Long interviewId){
        Interview interview  = findInterviewForCurrentUser(interviewId);
        return mapToResponse(interview);
    }

    public void deleteInterview (Long interviewId){
        Interview interview = findInterviewForCurrentUser(interviewId);

        interviewRepository.delete(interview);
    }


    public InterviewResponse updateInterview (Long interviewId,InterviewRequest request){

        Interview interview = findInterviewForCurrentUser(interviewId);

        InterviewOutcome oldOutcome = interview.getOutcome();

        interview.setInterviewDateTime(request.getInterviewDateTime());
        interview.setInterviewType(request.getInterviewType());
        if (request.getOutcome() == null) {
            interview.setOutcome(InterviewOutcome.PENDING);
        } else {
            interview.setOutcome(request.getOutcome());
        }
        interview.setInterviewerName(request.getInterviewerName());
        interview.setNotes(request.getNotes());

        Interview updatedInterview = interviewRepository.save(interview);

        if (oldOutcome != updatedInterview.getOutcome()){
            activityService.recordActivity(interview.getJobApplication(),
                    ActivityType.INTERVIEW_OUTCOME_UPDATED,
                    "Interview outcome updated from " + oldOutcome + " to " + updatedInterview.getOutcome());
        }else{
            activityService.recordActivity(interview.getJobApplication(),
                    ActivityType.INTERVIEW_UPDATED,
                    "Interview  details updated");
        }

        return mapToResponse(updatedInterview);


    }

   public InterviewResponse patchInterview(Long interviewId , InterviewPatchRequest request){

       Interview interview = findInterviewForCurrentUser(interviewId);

       InterviewOutcome oldOutcome = interview.getOutcome();

       if (request.getInterviewDateTime() != null) {
           interview.setInterviewDateTime(request.getInterviewDateTime());
       }

       if (request.getInterviewType() != null) {
           interview.setInterviewType(request.getInterviewType());
       }

       if (request.getOutcome() != null) {
           interview.setOutcome(request.getOutcome());
       }

       if (request.getInterviewerName() != null) {
           interview.setInterviewerName(request.getInterviewerName());
       }

       if (request.getNotes() != null) {
           interview.setNotes(request.getNotes());
       }

       Interview updatedInterview = interviewRepository.save(interview);

       if (oldOutcome != updatedInterview.getOutcome()){
           activityService.recordActivity(interview.getJobApplication(),
                   ActivityType.INTERVIEW_OUTCOME_UPDATED,
                   "Interview outcome updated from " + oldOutcome + " to " + updatedInterview.getOutcome());
       }else{
           activityService.recordActivity(interview.getJobApplication(),
                   ActivityType.INTERVIEW_UPDATED,
                   "Interview  details updated");
       }

       return mapToResponse(updatedInterview);

   }


    private Interview findInterviewForCurrentUser(Long interviewId){
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(()-> new InterviewNotFoundException(interviewId));

        Long applicationId   = interview.getJobApplication().getId();
        jobApplicationService.findApplicationOrThrow(applicationId );

        return interview;
    }






    private InterviewResponse mapToResponse(Interview interview) {
        return new InterviewResponse(
                interview.getId(),
                interview.getJobApplication().getId(),
                interview.getInterviewDateTime(),
                interview.getInterviewType(),
                interview.getOutcome(),
                interview.getInterviewerName(),
                interview.getNotes()
        );
    }
}
