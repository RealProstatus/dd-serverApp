package com.example.iot_serverApp.repository;

import com.example.iot_serverApp.model.TelemetryServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryServer, String> {
}
