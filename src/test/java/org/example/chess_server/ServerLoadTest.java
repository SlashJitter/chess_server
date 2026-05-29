package org.example.chess_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerLoadTest {

    @Value("${local.server.port}")
    private int port;

    private static final int NUMBER_OF_CLIENTS = 1000;
    private static final int THREAD_POOL_SIZE = 100;

    @Test
    void serverShouldHandle1000ClientsOnTestEndpoint() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_CLIENTS);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);
        AtomicLong totalResponseTime = new AtomicLong(0);

        HttpClient client = HttpClient.newHttpClient();

        long globalStart = System.currentTimeMillis();

        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            executor.submit(() -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:" + port + "/game/test"))
                            .GET()
                            .build();

                    long start = System.currentTimeMillis();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    long end = System.currentTimeMillis();

                    totalResponseTime.addAndGet(end - start);

                    if (response.statusCode() == 200 &&
                            response.body().equals("Server merge!")) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                    }

                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        long globalEnd = System.currentTimeMillis();

        executor.shutdown();

        long totalTime = globalEnd - globalStart;
        long averageResponseTime = totalResponseTime.get() / NUMBER_OF_CLIENTS;

        System.out.println("=== LOAD TEST SERVER ===");
        System.out.println("Clienti simulati: " + NUMBER_OF_CLIENTS);
        System.out.println("Thread-uri folosite: " + THREAD_POOL_SIZE);
        System.out.println("Cereri reusite: " + successCount.get());
        System.out.println("Cereri esuate: " + failCount.get());
        System.out.println("Timp total test: " + totalTime + " ms");
        System.out.println("Timp mediu raspuns: " + averageResponseTime + " ms");

        assertTrue(successCount.get() > 0);
        assertTrue(failCount.get() < NUMBER_OF_CLIENTS);
    }
}