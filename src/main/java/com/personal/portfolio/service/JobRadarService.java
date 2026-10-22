package com.personal.portfolio.service;

import com.personal.portfolio.model.JobApplication;
import com.personal.portfolio.model.ScannedJob;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class JobRadarService {

    private final JobService jobService;
    private final Map<String, ScannedJob> radarJobs = new ConcurrentHashMap<>();

    // User's Core Skills for Matching Engine
    private final Set<String> userSkills = Set.of(
            "java", "spring boot", "microservices", "rest api", "hibernate",
            "mysql", "jwt", "spring security", "jpa", "multithreading", "junit", "git"
    );

    public JobRadarService(JobService jobService) {
        this.jobService = jobService;
    }

    @PostConstruct
    public void init() {
        scanAllPlatforms();
    }

    public synchronized List<ScannedJob> scanAllPlatforms() {
        if (radarJobs.isEmpty()) {
            addRadarJob("rj-101", "Senior Java Microservices Developer", "Oracle", "LinkedIn",
                    "https://www.linkedin.com/jobs/search/?keywords=Senior%20Java%20Developer%20Oracle",
                    "Hyderabad (Hybrid)", "18 - 25 LPA", "5 - 8 Years",
                    List.of("Java 17", "Spring Boot", "Microservices", "REST API", "Docker", "MySQL"),
                    "15 mins ago");

            addRadarJob("rj-102", "Senior Software Engineer - Java / Spring Boot", "EPAM Systems", "Naukri",
                    "https://www.naukri.com/senior-java-developer-jobs-in-hyderabad-secunderabad",
                    "Hyderabad / Remote", "16 - 22 LPA", "4 - 7 Years",
                    List.of("Java", "Spring Boot", "REST APIs", "Microservices", "Hibernate", "JUnit"),
                    "42 mins ago");

            addRadarJob("rj-103", "Lead Java Backend Engineer", "Capgemini", "Indeed",
                    "https://in.indeed.com/jobs?q=Senior+Java+Developer&l=Hyderabad",
                    "Hyderabad", "17 - 23 LPA", "5 - 9 Years",
                    List.of("Core Java", "Spring Boot", "Microservices", "JWT Security", "MySQL"),
                    "1 hour ago");

            addRadarJob("rj-104", "Senior Java Full Stack Specialist", "Deloitte India", "LinkedIn",
                    "https://www.linkedin.com/jobs/search/?keywords=Java%20Developer%20Deloitte",
                    "Hyderabad / Pune (Remote Options)", "19 - 26 LPA", "5 - 8 Years",
                    List.of("Java 11/17", "Spring Boot", "Microservices", "REST API", "JavaScript", "SQL"),
                    "2 hours ago");

            addRadarJob("rj-105", "Staff Backend Developer - Distributed Systems", "Cognizant", "Naukri",
                    "https://www.naukri.com/senior-java-developer-jobs-in-hyderabad-secunderabad",
                    "Hyderabad", "15 - 20 LPA", "4 - 6 Years",
                    List.of("Spring Boot", "Microservices", "REST API", "Hibernate", "Agile"),
                    "3 hours ago");

            addRadarJob("rj-106", "Senior Java Engineer (Cloud Platforms)", "Virtusa", "Indeed",
                    "https://in.indeed.com/jobs?q=Senior+Java+Developer&l=Hyderabad",
                    "Hyderabad / Hybrid", "16 - 21 LPA", "5 - 7 Years",
                    List.of("Java", "Spring Boot", "Microservices", "Design Patterns", "MySQL"),
                    "Today");
        }
        return getScannedJobs();
    }

    private void addRadarJob(String id, String title, String company, String platform, String url,
                             String loc, String salary, String exp, List<String> skills, String postedTime) {
        // Calculate match score based on user skills
        List<String> matched = new ArrayList<>();
        int matchedCount = 0;
        for (String skill : skills) {
            String lower = skill.toLowerCase();
            for (String userSkill : userSkills) {
                if (lower.contains(userSkill)) {
                    matched.add(skill);
                    matchedCount++;
                    break;
                }
            }
        }

        int score = Math.min(98, 70 + (matchedCount * 6));
        String pitch = jobService.generatePitch(title, company, platform);

        ScannedJob job = new ScannedJob(id, title, company, platform, url, loc, salary, exp,
                skills, matched, score, postedTime, "New Alert", pitch);
        radarJobs.put(id, job);
    }

    public List<ScannedJob> getScannedJobs() {
        return radarJobs.values().stream()
                .sorted((a, b) -> Integer.compare(b.getMatchScore(), a.getMatchScore()))
                .collect(Collectors.toList());
    }

    public Optional<ScannedJob> getRadarJobById(String id) {
        return Optional.ofNullable(radarJobs.get(id));
    }

    public synchronized Map<String, Object> applyFromRadar(String radarJobId) {
        ScannedJob scanned = radarJobs.get(radarJobId);
        if (scanned == null) {
            return Map.of("success", false, "message", "Radar job not found");
        }

        scanned.setStatus("Applied");

        // Create a persistent JobApplication in JobService
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        JobApplication application = new JobApplication(
                UUID.randomUUID().toString().substring(0, 8),
                scanned.getTitle(),
                scanned.getCompany(),
                scanned.getPlatform(),
                scanned.getJobUrl(),
                today,
                "Applied",
                scanned.getSalary(),
                scanned.getLocation(),
                "Auto-applied from AI Job Radar (Match: " + scanned.getMatchScore() + "%)",
                scanned.getCustomPitch()
        );

        jobService.createJob(application);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Application logged successfully! Direct apply link ready.");
        response.put("directApplyUrl", scanned.getJobUrl());
        response.put("customPitch", scanned.getCustomPitch());
        response.put("platform", scanned.getPlatform());
        response.put("company", scanned.getCompany());
        response.put("jobTitle", scanned.getTitle());
        return response;
    }
}
