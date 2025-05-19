package com.example.iot_serverApp.service;

import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TelemetryService {
    @Autowired
    private TelemetryRepository telemetryRepository;

    // @Transactional
    public List<TelemetryServer> saveTelemetry(String deviceId, Map<String, Object> metrics, String status) {
        List<TelemetryServer> saved = new ArrayList<TelemetryServer>();
        Instant timestamp = Instant.now();
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            TelemetryServer telemetry = new TelemetryServer();
            telemetry.setDeviceId(deviceId);
            telemetry.setMetricName(entry.getKey());
            telemetry.setMetricValue(Double.parseDouble((entry.getValue()).toString()));
            telemetry.setTimestamp(timestamp);
            telemetry.setStatus(status);
            saved.add(telemetryRepository.save(telemetry));
        }
        return saved;
    }
}
