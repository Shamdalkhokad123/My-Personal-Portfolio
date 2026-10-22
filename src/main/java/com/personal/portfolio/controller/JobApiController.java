package com.personal.portfolio.controller;

import com.personal.portfolio.model.JobApplication;
import com.personal.portfolio.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobApiController {

    private final JobService jobService;

    public JobApiController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getJobById(@PathVariable String id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JobApplication> createJob(@RequestBody JobApplication job) {
        JobApplication created = jobService.createJob(job);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateJob(@PathVariable String id, @RequestBody JobApplication job) {
        return jobService.updateJob(id, job)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        if (jobService.deleteJob(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(jobService.getStats());
    }

    @PostMapping("/generate-pitch")
    public ResponseEntity<Map<String, String>> generatePitch(@RequestBody Map<String, String> request) {
        String title = request.getOrDefault("jobTitle", "Senior Java Developer");
        String company = request.getOrDefault("company", "Hiring Team");
        String platform = request.getOrDefault("platform", "LinkedIn");
        String pitch = jobService.generatePitch(title, company, platform);
        return ResponseEntity.ok(Map.of("pitch", pitch));
    }
}
