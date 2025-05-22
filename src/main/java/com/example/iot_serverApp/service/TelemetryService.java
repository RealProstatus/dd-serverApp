package com.example.iot_serverApp.service;

import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TelemetryService {
    @Autowired
    private TelemetryRepository telemetryRepository;

    @Value("${water.level.threshold}")
    private Double threshold;

    public List<TelemetryServer> saveTelemetry(String deviceId, Map<String, Object> metrics, String status) {
        List<TelemetryServer> saved = new ArrayList<>();
        Instant timestamp = Instant.now();
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            TelemetryServer telemetry = new TelemetryServer();
            telemetry.setDeviceId(deviceId);
            telemetry.setMetricName(entry.getKey());
            Double metricValue = Double.parseDouble(entry.getValue().toString());
            telemetry.setMetricValue(metricValue);
            telemetry.setTimestamp(timestamp);
            telemetry.setStatus(metricValue < threshold ? "LOW_WATER" : status);
            saved.add(telemetryRepository.save(telemetry));
        }
        return saved;
    }
}