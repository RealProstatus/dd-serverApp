package com.example.iot_serverApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "telemetry_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String metricName;

    @Column(nullable = false)
    private Double metricValue;

    @Column(nullable = false)
    private String status;

    public void    setTimestamp(Instant timestamp)   { this.timestamp = timestamp; }
    public Instant getTimestamp()                    { return timestamp; }

    public void   setMetricName(String metricName)   { this.metricName = metricName; }
    public String getMetricName()                    { return metricName; }

    public void   setMetricValue(Double metricValue) { this.metricValue = metricValue;}
    public Double getMetricValue()                   { return metricValue; }

    public void   setStatus(String status)           { this.status = status; }
    public String getStatus()                        { return status; }

    public void   setDeviceId(String deviceId)       { this.deviceId = deviceId; }
    public String getDeviceId()                      { return deviceId; }
}