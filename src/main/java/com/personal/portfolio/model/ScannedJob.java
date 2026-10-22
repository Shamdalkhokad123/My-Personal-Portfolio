package com.personal.portfolio.model;

import java.io.Serializable;
import java.util.List;

public class ScannedJob implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String company;
    private String platform; // "LinkedIn", "Naukri", "Indeed"
    private String jobUrl;
    private String location;
    private String salary;
    private String experience;
    private List<String> requiredSkills;
    private List<String> matchedSkills;
    private int matchScore; // e.g. 95%
    private String postedTime;
    private String status; // "New Alert", "Applied", "Saved"
    private String customPitch;

    public ScannedJob() {
    }

    public ScannedJob(String id, String title, String company, String platform, String jobUrl,
                      String location, String salary, String experience, List<String> requiredSkills,
                      List<String> matchedSkills, int matchScore, String postedTime, String status, String customPitch) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.platform = platform;
        this.jobUrl = jobUrl;
        this.location = location;
        this.salary = salary;
        this.experience = experience;
        this.requiredSkills = requiredSkills;
        this.matchedSkills = matchedSkills;
        this.matchScore = matchScore;
        this.postedTime = postedTime;
        this.status = status;
        this.customPitch = customPitch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomPitch() {
        return customPitch;
    }

    public void setCustomPitch(String customPitch) {
        this.customPitch = customPitch;
    }
}
