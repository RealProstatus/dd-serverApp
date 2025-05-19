package com.example.iot_serverApp.model;

import java.time.Instant;
import java.util.Map;

public class TelemetryClient {
    private String              deviceId;
    private Instant             timestamp;
    private Map<String, Object> metrics;
    private String              status;

    public TelemetryClient(String deviceId, Instant timestamp, Map<String, Object> metrics, String status) {
        this.deviceId  = deviceId;
        this.timestamp = timestamp;
        this.metrics   = metrics;
        this.status    = status;
    }

    public String getDeviceId()                                        { return deviceId;}
    public void   setDeviceId(String deviceId)                         { this.deviceId = deviceId; }

    public Instant getTimestamp()                                      { return timestamp; }
    public void    setTimestamp(Instant timestamp)                     { this.timestamp = timestamp; }

    public Map<String, Object> getMetrics()                            { return metrics; }
    public void                setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }

    public String getStatus()                                          { return status; }
    public void   setStatus(String status)                             { this.status = status; }

    @Override
    public String toString() {
        return "Telemetry{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", metrics=" + metrics +
                ", status='" + status + '\'' +
                '}';
    }
}
