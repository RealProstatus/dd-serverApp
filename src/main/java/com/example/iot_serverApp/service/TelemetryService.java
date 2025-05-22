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
        if (metrics == null) {
            return new ArrayList<>(); // Возвращаем пустой список, если metrics == null
        }

        List<TelemetryServer> saved = new ArrayList<>();
        Instant timestamp = Instant.now();
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                System.err.println("Skipping entry with null key or value: " + entry);
                continue;
            }

            Double metricValue;
            try {
                metricValue = Double.parseDouble(entry.getValue().toString());
            } catch (NumberFormatException e) {
                System.err.println("Invalid metric value for " + entry.getKey() + ": " + entry.getValue());
                continue;
            }

            TelemetryServer telemetry = new TelemetryServer();
            telemetry.setDeviceId(deviceId);
            telemetry.setMetricName(entry.getKey());
            telemetry.setMetricValue(metricValue);
            telemetry.setTimestamp(timestamp);
            telemetry.setStatus(metricValue < threshold ? "LOW_WATER" : status);
            saved.add(telemetryRepository.save(telemetry));
        }
        return saved;
    }
}