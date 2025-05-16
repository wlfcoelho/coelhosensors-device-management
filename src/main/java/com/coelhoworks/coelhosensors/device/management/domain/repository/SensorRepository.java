package com.coelhoworks.coelhosensors.device.management.domain.repository;

import com.coelhoworks.coelhosensors.device.management.domain.model.Sensor;
import com.coelhoworks.coelhosensors.device.management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
