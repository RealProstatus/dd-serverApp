package com.example.iot_serverApp.service;

import com.example.iot_serverApp.model.TelemetryServer;
import com.example.iot_serverApp.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    @Value("${tgbot.CHAT_ID}")
    private String CHAT_ID;

    @Value("{tgbot.API}")
    private String BOT_TOKEN;
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
            if(metricValue < threshold) {
                telemetry.setStatus("LOW_WATER");
                try {
                    sendAlert();
                } catch (IOException e) {
                    System.err.println(e.toString());
                } catch (InterruptedException e) {
                    System.err.println(e.toString());
                }


            }
            else {
                telemetry.setStatus(status);
            }
            telemetry.setStatus(metricValue < threshold ? "LOW_WATER" : status);
            saved.add(telemetryRepository.save(telemetry));

        }
        return saved;
    }

    private void  sendAlert() throws IOException, InterruptedException {

        String jsonPayload = String.format("""
            {
                "chat_id": "%s",
                "text": "Почва засыхает, полейте ваш цветок!",
                "parse_mode": "Markdown",
                "reply_markup": {
                    "inline_keyboard": [
                        [{"text": "Полить", "callback_data": "btn1"}],
                    ]
                }
            }
            """, CHAT_ID);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}