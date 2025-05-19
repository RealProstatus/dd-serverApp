package com.example.iot_serverApp.controller;

import com.example.iot_serverApp.model.TelemetryClient;
import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<List<TelemetryServer>> saveTelemetry(@Valid @RequestBody TelemetryClient telemetry) {
        return ResponseEntity.ok(
                telemetryService.saveTelemetry(
                        telemetry.getDeviceId(),
                        telemetry.getMetrics(),
                        telemetry.getStatus()
                )
        );
    }
}
