package com.relaximus.azuresbreactivetest;

import com.azure.core.util.BinaryData;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.relaximus.azuresbreactivetest.AsyncReceiver.RawData;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@SpringBootApplication
public class AzureSbReactiveTestApplication {

    @Autowired
    MockData mockData;
    @Autowired
    AsyncReceiver receiver;

    public static void main(String[] args) {
        SpringApplication.run(AzureSbReactiveTestApplication.class, args);
    }

    @EventListener
    public void run(ApplicationStartedEvent event) {
        log.info("Creating receiver.");
        receiveMonthlyData()
                .subscribe(d -> log.info("Monthly data: {}", d));

        log.info("Sending data.");
        sendMockData().blockLast();
    }

    private Flux<RawData> receiveMonthlyData() {
        return receiver.receive()
                .windowUntilChanged(d -> d.timestamp().withDayOfMonth(1))
                .flatMap(f ->
                        f.reduce((rawData, rawData2) ->
                                new RawData(
                                        rawData.timestamp().withDayOfMonth(1),
                                        rawData.value() + rawData2.value())
                        ));
    }

    private Flux<Void> sendMockData() {
        final Random random = new Random();
        final LocalDate startDate = LocalDate.ofYearDay(2022, 1);
        // 100 days
        return Flux.range(0, 300)
                .delayElements(Duration.ofSeconds(1))
                .map(startDate::plusDays)

                // creating JSON message with random values for every day
                .map(day -> new RawData(day, random.nextDouble(100.0)))
                .map(rd -> new ServiceBusMessage(BinaryData.fromObject(rd))
                        .setContentType("application/json"))
                .flatMap(mockData::send);
    }
}
