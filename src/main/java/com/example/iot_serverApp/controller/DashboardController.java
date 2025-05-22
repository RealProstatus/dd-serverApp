package com.example.iot_serverApp.controller;

import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private TelemetryRepository telemetryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TelemetryServer> latestData = telemetryRepository.findTop10ByOrderByTimestampDesc();
        model.addAttribute("data", latestData);
        return "dashboard";
    }
}