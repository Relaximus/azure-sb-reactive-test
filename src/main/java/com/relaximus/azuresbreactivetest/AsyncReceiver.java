package com.relaximus.azuresbreactivetest;

import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AsyncReceiver {

    private final ServiceBusReceiverAsyncClient client;

    public AsyncReceiver(ServiceBusReceiverAsyncClient client) {
        this.client = client;
    }

    public Flux<RawData> receive() {
        return client.receiveMessages()
                .map(sbm -> sbm.getBody().toObject(RawData.class));

    }

    public record RawData(LocalDate timestamp, Double value) {

    }

    ;
}
