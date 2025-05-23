package com.coelhoworks.coelhosensors.device.management.api.controller;

import com.coelhoworks.coelhosensors.device.management.api.client.SensorMonitoringClient;
import com.coelhoworks.coelhosensors.device.management.api.model.SensorDetailOutput;
import com.coelhoworks.coelhosensors.device.management.api.model.SensorInput;
import com.coelhoworks.coelhosensors.device.management.api.model.SensorMonitoringOuput;
import com.coelhoworks.coelhosensors.device.management.api.model.SensorOutput;
import com.coelhoworks.coelhosensors.device.management.common.IdGenerator;
import com.coelhoworks.coelhosensors.device.management.domain.model.Sensor;
import com.coelhoworks.coelhosensors.device.management.domain.model.SensorId;
import com.coelhoworks.coelhosensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

  private final SensorRepository sensorRepository;
  private final SensorMonitoringClient sensorMonitoringClient;

  @PutMapping("{sensorId}/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void enable(@PathVariable TSID sensorId) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    sensor.setEnabled(true);

    sensorRepository.save(sensor);

    sensorMonitoringClient.enabledMonitoring(sensorId);
  }

  @DeleteMapping("{sensorId}/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void diseble(@PathVariable TSID sensorId) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    sensor.setEnabled(false);

    sensorRepository.save(sensor);

    sensorMonitoringClient.disabledMonitoring(sensorId);
  }

  @DeleteMapping("{sensorId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteSensor(@PathVariable TSID sensorId) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    sensorRepository.delete(sensor);
  }

  @PutMapping("{sensorId}")
  @ResponseStatus(HttpStatus.OK)
  public SensorOutput atualizationSensor(@PathVariable TSID sensorId,
                                         @RequestBody SensorInput input) {

    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    sensor.setName(input.getName());
    sensor.setIp(sensor.getIp());
    sensor.setLocation(sensor.getLocation());
    sensor.setProtocol(sensor.getProtocol());
    sensor.setModel(sensor.getModel());

    sensor = sensorRepository.saveAndFlush(sensor);

    return convertToModel(sensor);
  }

  @GetMapping
  public Page<SensorOutput> search(@PageableDefault Pageable pageable) {

    Page<Sensor> sensors = sensorRepository.findAll(pageable);
    return sensors.map(this::convertToModel);

  }

  @GetMapping("{sensorId}")
  public SensorOutput getOne(@PathVariable TSID sensorId) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return convertToModel(sensor);
  }

  @GetMapping("{sensorId}/detail")
  public SensorDetailOutput getOneWithDetail(@PathVariable TSID sensorId) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    SensorMonitoringOuput monitoringOuput = sensorMonitoringClient.getDetail(sensorId);

    SensorOutput sensorOutput = convertToModel(sensor);
    return SensorDetailOutput.builder()
            .monitoring(monitoringOuput)
            .sensor(sensorOutput)
            .build();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SensorOutput create(@RequestBody SensorInput input) {
    Sensor sensor = Sensor.builder()
            .id(new SensorId(IdGenerator.genereteTSID()))
            .name(input.getName())
            .ip(input.getIp())
            .location(input.getLocation())
            .protocol(input.getProtocol())
            .model(input.getModel())
            .enabled(false)
            .build();

    sensor = sensorRepository.saveAndFlush(sensor);

    return convertToModel(sensor);
  }

  private SensorOutput convertToModel(Sensor sensor) {
    return SensorOutput.builder()
            .id(sensor.getId().getValue())
            .name(sensor.getName())
            .ip(sensor.getIp())
            .location(sensor.getLocation())
            .protocol(sensor.getProtocol())
            .model(sensor.getModel())
            .enabled(sensor.getEnabled())
            .build();
  }
}
