package com.gradtrack.dto;

public class TaskDashboardResponse {
    private long totalTasks;
    private long todo;
    private long inProgress;
    private long completed;
    private long cancelled;
    private long dueToday;
    private long overdue;
    private long upcoming;

    public TaskDashboardResponse() {
    }

    public TaskDashboardResponse(long totalTasks, long todo, long inProgress, long completed,
                                 long cancelled, long dueToday, long overdue, long upcoming) {
        this.totalTasks = totalTasks;
        this.todo = todo;
        this.inProgress = inProgress;
        this.completed = completed;
        this.cancelled = cancelled;
        this.dueToday = dueToday;
        this.overdue = overdue;
        this.upcoming = upcoming;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getTodo() {
        return todo;
    }

    public void setTodo(long todo) {
        this.todo = todo;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }

    public long getDueToday() {
        return dueToday;
    }

    public void setDueToday(long dueToday) {
        this.dueToday = dueToday;
    }

    public long getOverdue() {
        return overdue;
    }

    public void setOverdue(long overdue) {
        this.overdue = overdue;
    }

    public long getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(long upcoming) {
        this.upcoming = upcoming;
    }
}
