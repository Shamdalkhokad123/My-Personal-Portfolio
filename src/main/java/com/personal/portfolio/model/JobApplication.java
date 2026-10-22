package com.personal.portfolio.model;

import java.io.Serializable;

public class JobApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String jobTitle;
    private String company;
    private String platform; // "LinkedIn", "Naukri", "Indeed", "Direct"
    private String jobUrl;
    private String appliedDate;
    private String status; // "Applied", "Under Review", "Interviewing", "Offer", "Rejected"
    private String salaryPackage;
    private String location;
    private String notes;
    private String tailoredPitch;

    public JobApplication() {
    }

    public JobApplication(String id, String jobTitle, String company, String platform, String jobUrl,
                          String appliedDate, String status, String salaryPackage, String location,
                          String notes, String tailoredPitch) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.platform = platform;
        this.jobUrl = jobUrl;
        this.appliedDate = appliedDate;
        this.status = status;
        this.salaryPackage = salaryPackage;
        this.location = location;
        this.notes = notes;
        this.tailoredPitch = tailoredPitch;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalaryPackage() {
        return salaryPackage;
    }

    public void setSalaryPackage(String salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTailoredPitch() {
        return tailoredPitch;
    }

    public void setTailoredPitch(String tailoredPitch) {
        this.tailoredPitch = tailoredPitch;
    }
}
