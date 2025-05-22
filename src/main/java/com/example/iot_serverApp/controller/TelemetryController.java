package com.example.iot_serverApp.controller;

import com.example.iot_serverApp.model.TelemetryClient;
import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;

    @PostMapping("/telemetry")
    public List<TelemetryServer> receiveTelemetry(@RequestBody TelemetryClient telemetry) {
        return telemetryService.saveTelemetry(
                telemetry.getDeviceId(),
                telemetry.getMetrics(),
                telemetry.getStatus()
        );
    }
}