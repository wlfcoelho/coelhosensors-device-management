package com.coelhoworks.coelhosensors.device.management.api.client.impl;

import com.coelhoworks.coelhosensors.device.management.api.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

  private final RestClient restClient;

  public SensorMonitoringClientImpl(RestClient.Builder builder) {
    this.restClient = builder.baseUrl("http://localhost:8082")
            .build();
  }


  @Override
  public void enabledMonitoring(TSID sensorId) {
    restClient.put()
            .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
            .retrieve()
            .toBodilessEntity();
  }

  @Override
  public void disabledMonitoring(TSID sensorId) {
    restClient.delete()
            .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
            .retrieve()
            .toBodilessEntity();
  }
}
