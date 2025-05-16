package com.coelhoworks.coelhosensors.device.management.api.controller;

import com.coelhoworks.coelhosensors.device.management.api.model.SensorInput;
import com.coelhoworks.coelhosensors.device.management.common.IdGenerator;
import com.coelhoworks.coelhosensors.device.management.domain.model.Sensor;
import com.coelhoworks.coelhosensors.device.management.domain.model.SensorId;
import com.coelhoworks.coelhosensors.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor create(@RequestBody SensorInput input){
        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.genereteTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();

        return sensorRepository.saveAndFlush(sensor);
    }
}
