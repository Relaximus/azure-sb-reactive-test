## Reactive client for Azure Service Bus example

It's a short example of possible timeseries window - aggregation,
which might be done with reactive stack, supported by [Azure SDK](https://github.com/Azure/azure-sdk-for-java/tree/main/sdk/servicebus/azure-messaging-servicebus).

### Startup

Configure `connection-string` in the **application.properties** file and just start spring application:

```shell
./gradlew bootRun
```

### Main workflow
It sends 300 days of random data with the 1s delay between messages. 
Receiver waits till the month changes and sends to the console
summary of the data for the month.
