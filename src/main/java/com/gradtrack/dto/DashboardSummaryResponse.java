package com.gradtrack.dto;

public class DashboardSummaryResponse {

    private long totalApplications;
    private long saved;
    private long applied;
    private long onlineTest;
    private long interview;
    private long offer;
    private long rejected;
    private long withdrawn;

    public DashboardSummaryResponse() {
    }
    public DashboardSummaryResponse(long totalApplications, long saved, long applied, long onlineTest,
                                    long interview, long offer, long rejected, long withdrawn) {
        this.totalApplications = totalApplications;
        this.saved = saved;
        this.applied = applied;
        this.onlineTest = onlineTest;
        this.interview = interview;
        this.offer = offer;
        this.rejected = rejected;
        this.withdrawn = withdrawn;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getSaved() {
        return saved;
    }

    public void setSaved(long saved) {
        this.saved = saved;
    }

    public long getApplied() {
        return applied;
    }

    public void setApplied(long applied) {
        this.applied = applied;
    }

    public long getOnlineTest() {
        return onlineTest;
    }

    public void setOnlineTest(long onlineTest) {
        this.onlineTest = onlineTest;
    }

    public long getOffer() {
        return offer;
    }

    public void setOffer(long offer) {
        this.offer = offer;
    }

    public long getInterview() {
        return interview;
    }

    public void setInterview(long interview) {
        this.interview = interview;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }

    public long getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(long withdrawn) {
        this.withdrawn = withdrawn;
    }
}
