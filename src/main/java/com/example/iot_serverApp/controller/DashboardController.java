package com.example.iot_serverApp.controller;

import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private TelemetryRepository telemetryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TelemetryServer> latestData = telemetryRepository.findTop10ByOrderByTimestampDesc();
        if (latestData == null) {
            latestData = Collections.emptyList();
        }
        System.out.println("Latest data: " + latestData);
        for (TelemetryServer item : latestData) {
            System.out.println("Metric value: " + item.getMetricValue() + ", Status: " + item.getStatus());
        }
        model.addAttribute("data", latestData);
        return "dashboard";
    }

    @GetMapping("/dashboard/data")
    @ResponseBody
    public List<TelemetryServer> getTelemetryData() {
        List<TelemetryServer> latestData = telemetryRepository.findTop10ByOrderByTimestampDesc();
        if (latestData == null) {
            return Collections.emptyList();
        }
        return latestData;
    }
}