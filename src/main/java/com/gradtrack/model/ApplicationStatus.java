package com.gradtrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


public enum ApplicationStatus {
    SAVED,
    APPLIED,
    ONLINE_TEST,
    INTERVIEW,
    OFFER,
    REJECTED,
    WITHDRAWN
}