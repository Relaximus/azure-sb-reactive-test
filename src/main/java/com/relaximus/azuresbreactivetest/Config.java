package com.relaximus.azuresbreactivetest;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${connection-string}")
    private String CONNECTION_STRING;

    @Bean
    public ServiceBusReceiverAsyncClient receiverClient() {
        return new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .receiver()
                .topicName("reactor")
                .subscriptionName("test")
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusSenderAsyncClient senderClient() {
        return new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .sender()
                .topicName("reactor")
                .buildAsyncClient();
    }

}
