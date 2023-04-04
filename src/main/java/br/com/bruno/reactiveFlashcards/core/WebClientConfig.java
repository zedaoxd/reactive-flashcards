package br.com.bruno.reactiveFlashcards.core;

import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    WebClient webClient(WebClient.Builder builder, ClientHttpConnector connector) {
        log.info("Creating WebClient");
        return builder.clientConnector(connector).build();
    }

    @Bean
    ClientHttpConnector clientHttpConnector(HttpClient httpClient) {
        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    HttpClient httpClient(@Value("{http-client.response-timeout}") Long timeout,
                          @Value("{http-client.read-timeout}") Long readTimeout) {
        return HttpClient.create()
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MICROSECONDS)));
    }
}
