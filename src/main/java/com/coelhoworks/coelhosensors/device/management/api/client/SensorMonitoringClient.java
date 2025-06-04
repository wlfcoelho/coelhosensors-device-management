package com.coelhoworks.coelhosensors.device.management.api.client;

import com.coelhoworks.coelhosensors.device.management.api.model.SensorMonitoringOuput;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {

  @PutExchange("/enable")
  void enabledMonitoring(@PathVariable TSID sensorId);

  @DeleteExchange("/enable")
  void disabledMonitoring(@PathVariable TSID sensorId);

  @GetExchange
  SensorMonitoringOuput getDetail(@PathVariable TSID sensorId);
}
