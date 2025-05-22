package com.coelhoworks.coelhosensors.device.management.api.client;

import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

  void enabledMonitoring(TSID sensorId);
  void disabledMonitoring(TSID sensorId);
}
