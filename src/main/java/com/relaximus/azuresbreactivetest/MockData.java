package com.relaximus.azuresbreactivetest;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MockData {

    private final ServiceBusSenderAsyncClient client;

    public MockData(ServiceBusSenderAsyncClient client) {
        this.client = client;
    }

    public Mono<Void> send(ServiceBusMessage messages) {
        return client.sendMessage(messages);
    }

}
