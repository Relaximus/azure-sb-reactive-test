package com.relaximus.azuresbreactivetest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class AzureSbReactiveTestApplicationTests {

    @Test
    void contextLoads() {
        var tenSecs = Flux.interval(Duration.ofSeconds(1)).map(v -> "10: "+v).take(10);
        var fiveSecs = Flux.interval(Duration.ofSeconds(2)).map(v -> "5: "+v).take(5);

        Exceptions.propagate()

        tenSecs.doFirst(() -> System.out.println("first"))
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("complete"))
                .blockLast();
//        tenSecs.mergeWith(fiveSecs).map(v -> {
//            System.out.println(v);
//            return v;
//        }).blockLast();
//        StepVerifier.withVirtualTime(() -> tenSecs)
//                .expectSubscription()
//                .thenAwait(Duration.ofSeconds(10))
//                .expectNextCount(10)
//                .expectComplete()
//                .verify();
//        tenSecs.map(v -> {
//            System.out.println(v);
//            return v;
//        }).blockLast();
    }

}
