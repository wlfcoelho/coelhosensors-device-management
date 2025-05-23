package com.coelhoworks.coelhosensors.device.management.api.client;

import com.coelhoworks.coelhosensors.device.management.api.model.SensorMonitoringOuput;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

  void enabledMonitoring(TSID sensorId);
  void disabledMonitoring(TSID sensorId);
  SensorMonitoringOuput getDetail(TSID sensorId);
}
