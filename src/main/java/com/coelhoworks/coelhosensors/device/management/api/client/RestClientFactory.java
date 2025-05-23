package com.coelhoworks.coelhosensors.device.management.api.client;

import com.coelhoworks.coelhosensors.device.management.api.client.impl.SensorMonitoringClientBadGatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

  private final RestClient.Builder builder;

  public RestClient temperatureMonitoringClient() {
    return builder.baseUrl("http://localhost:8082")
            .requestFactory(generateClientHttpRequestFactory())
            .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
              throw new SensorMonitoringClientBadGatewayException();
            })
            .build();
  }

  private ClientHttpRequestFactory generateClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

    factory.setReadTimeout(Duration.ofSeconds(5));
    factory.setReadTimeout(Duration.ofSeconds(3));

    return factory;
  }
}
