package com.personal.portfolio.controller;

import com.personal.portfolio.model.ScannedJob;
import com.personal.portfolio.service.JobRadarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/radar")
@CrossOrigin(origins = "*")
public class JobRadarApiController {

    private final JobRadarService radarService;

    public JobRadarApiController(JobRadarService radarService) {
        this.radarService = radarService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<ScannedJob>> getScannedJobs() {
        return ResponseEntity.ok(radarService.getScannedJobs());
    }

    @PostMapping("/scan")
    public ResponseEntity<List<ScannedJob>> triggerScan() {
        return ResponseEntity.ok(radarService.scanAllPlatforms());
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<Map<String, Object>> applyFromRadar(@PathVariable String id) {
        Map<String, Object> result = radarService.applyFromRadar(id);
        if (Boolean.TRUE.equals(result.get("success"))) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
