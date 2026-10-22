package com.personal.portfolio.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.portfolio.model.JobApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final Map<String, JobApplication> jobStorage = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final File dataFile = new File("data/jobs.json");

    @PostConstruct
    public void init() {
        loadJobsFromFile();
        if (jobStorage.isEmpty()) {
            seedInitialJobs();
            saveJobsToFile();
        }
    }

    private synchronized void loadJobsFromFile() {
        if (dataFile.exists()) {
            try {
                List<JobApplication> list = objectMapper.readValue(dataFile, new TypeReference<List<JobApplication>>() {});
                for (JobApplication job : list) {
                    if (job.getId() != null) {
                        jobStorage.put(job.getId(), job);
                    }
                }
            } catch (IOException e) {
                System.err.println("Could not load jobs from file: " + e.getMessage());
            }
        }
    }

    private synchronized void saveJobsToFile() {
        try {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            objectMapper.writeValue(dataFile, new ArrayList<>(jobStorage.values()));
        } catch (IOException e) {
            System.err.println("Could not save jobs to file: " + e.getMessage());
        }
    }

    private void seedInitialJobs() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        addSeedJob("1", "Senior Java Developer", "HCLTech", "Naukri",
                "https://www.naukri.com/job-listings-java-developer-hcltech-hyderabad-6-to-10-years-of-experience",
                today, "Under Review", "15 - 20 LPA", "Hyderabad",
                "Applied via Naukri. Microservices & Spring Boot focus. Contacted recruitment team.",
                "Hi Hiring Team at HCLTech, I have applied for the Senior Java Developer role. With 5+ years of experience specializing in Java 17, Spring Boot, and scalable Microservices at Dot eVentures, I can deliver immediate value.");

        addSeedJob("2", "Senior Java Developer", "Kumaran Systems", "LinkedIn",
                "https://www.linkedin.com/jobs/search/?currentJobId=4482139812",
                today, "Applied", "14 - 18 LPA", "Remote / Hybrid",
                "Applied via LinkedIn Easy Apply. Resume and portfolio submitted.",
                "Hello Hiring Manager at Kumaran Systems, I am eager to contribute as a Senior Java Developer, bringing expertise in REST architecture, JWT security, and enterprise database tuning.");

        addSeedJob("3", "Java Full Stack Developer", "TechDoQuest", "LinkedIn",
                "https://www.linkedin.com/jobs/search/?currentJobId=4482139812",
                today, "Interviewing", "16 - 22 LPA", "Hybrid",
                "HR responded on LinkedIn. Technical discussion scheduled.",
                "Dear TechDoQuest Team, Thank you for reviewing my profile. Looking forward to discussing how my experience leading 10 developers and designing 50+ REST APIs aligns with your technical needs.");

        addSeedJob("4", "Senior Java Software Engineer", "TrueID Technologies", "LinkedIn",
                "https://www.linkedin.com/jobs/search/?currentJobId=4467382877",
                today, "Applied", "15 - 19 LPA", "Hyderabad",
                "Applied via LinkedIn. Identity & enterprise platform role.",
                "Hi TrueID Team, With strong background in secure Spring Security/JWT architectures and resilient microservices, I am excited about your identity security platform.");

        addSeedJob("5", "Java Backend Engineer", "CMIND", "LinkedIn",
                "https://www.linkedin.com/jobs/search/",
                today, "Applied", "14 - 17 LPA", "Remote",
                "Direct recruiter outreach and application logged.",
                "Hello CMIND Recruitment Team, I recently applied for your Java Backend position. Having built enterprise systems with 35% latency optimization, I am eager to connect.");

        addSeedJob("6", "Senior Java Developer", "Tileti Eemeze Soft Tech", "Naukri",
                "https://www.naukri.com/job-listings-java-developer-senior-tileti-eemeze-soft-tech",
                today, "Applied", "13 - 16 LPA", "Hyderabad",
                "Applied on Naukri. Profile matched 100% key skills.",
                "Hi Hiring Team, My background in Core Java, Spring Boot, Hibernate, and MySQL aligns closely with your position requirements.");
    }

    private void addSeedJob(String id, String title, String company, String platform, String url,
                            String date, String status, String salary, String loc, String notes, String pitch) {
        JobApplication job = new JobApplication(id, title, company, platform, url, date, status, salary, loc, notes, pitch);
        jobStorage.put(id, job);
    }

    public List<JobApplication> getAllJobs() {
        return jobStorage.values().stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .collect(Collectors.toList());
    }

    public Optional<JobApplication> getJobById(String id) {
        return Optional.ofNullable(jobStorage.get(id));
    }

    public synchronized JobApplication createJob(JobApplication job) {
        if (job.getId() == null || job.getId().trim().isEmpty()) {
            job.setId(UUID.randomUUID().toString().substring(0, 8));
        }
        if (job.getAppliedDate() == null || job.getAppliedDate().trim().isEmpty()) {
            job.setAppliedDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (job.getStatus() == null || job.getStatus().trim().isEmpty()) {
            job.setStatus("Applied");
        }
        if (job.getTailoredPitch() == null || job.getTailoredPitch().trim().isEmpty()) {
            job.setTailoredPitch(generatePitch(job.getJobTitle(), job.getCompany(), job.getPlatform()));
        }
        jobStorage.put(job.getId(), job);
        saveJobsToFile();
        return job;
    }

    public synchronized Optional<JobApplication> updateJob(String id, JobApplication updated) {
        if (!jobStorage.containsKey(id)) {
            return Optional.empty();
        }
        updated.setId(id);
        jobStorage.put(id, updated);
        saveJobsToFile();
        return Optional.of(updated);
    }

    public synchronized boolean deleteJob(String id) {
        if (jobStorage.containsKey(id)) {
            jobStorage.remove(id);
            saveJobsToFile();
            return true;
        }
        return false;
    }

    public String generatePitch(String jobTitle, String company, String platform) {
        String cleanTitle = (jobTitle != null && !jobTitle.isEmpty()) ? jobTitle : "Senior Java Developer";
        String cleanCompany = (company != null && !company.isEmpty()) ? company : "the team";

        return "Hi Hiring Team at " + cleanCompany + ",\n\n" +
                "I am writing to express my strong interest in the " + cleanTitle + " role. " +
                "With over 5 years of hands-on experience in Java, Spring Boot, Microservices, and REST API architecture at Dot eVentures and Virtusa, " +
                "I have a proven record of leading engineering teams of 10+, designing secure JWT enterprise systems, and driving a 35% boost in system performance.\n\n" +
                "My key strengths include:\n" +
                "• Spring Boot, Microservices, Spring Security & JWT authentication\n" +
                "• Relational database optimization (MySQL) & Hibernate ORM\n" +
                "• Clean code principles, JUnit testing, and Agile Scrum delivery\n\n" +
                "I would welcome the opportunity to discuss how my technical leadership and background can support " + cleanCompany + "'s goals. " +
                "My portfolio and code samples are available at https://github.com/Shamdalkhokad123.\n\n" +
                "Best regards,\n" +
                "Shamdal Khokad\n" +
                "Senior Java Developer | +91-9890824574 | kshamdal@gmail.com";
    }

    public Map<String, Object> getStats() {
        List<JobApplication> all = getAllJobs();
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", all.size());
        stats.put("applied", all.stream().filter(j -> "Applied".equalsIgnoreCase(j.getStatus())).count());
        stats.put("underReview", all.stream().filter(j -> "Under Review".equalsIgnoreCase(j.getStatus())).count());
        stats.put("interviewing", all.stream().filter(j -> "Interviewing".equalsIgnoreCase(j.getStatus())).count());
        stats.put("offers", all.stream().filter(j -> "Offer".equalsIgnoreCase(j.getStatus())).count());
        stats.put("rejected", all.stream().filter(j -> "Rejected".equalsIgnoreCase(j.getStatus())).count());
        return stats;
    }
}
