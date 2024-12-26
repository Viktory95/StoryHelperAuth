package com.vi.StoryHelperAuth.rest;

import com.vi.StoryHelperAuth.model.Health;
import com.vi.StoryHelperAuth.model.HealthStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HealthResource {
    @GetMapping(
            value = "/health",
            produces = "application/json")
    public ResponseEntity<Health> getHealth() {
        log.debug("REST request to get the Health Status");
        final var health = new Health();
        health.setStatus(HealthStatus.UP);
        return ResponseEntity.ok().body(health);
    }
}
