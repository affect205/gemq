package org.alexside.gemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Component
public class Publisher implements IPublisher {
    @Autowired
    private IMessageGenerator generator;
    private Map<ISubscriber, PriorityBlockingQueue<Message>> messageQueue = new HashMap<>();
    private ScheduledExecutorService scheduledPool = Executors.newSingleThreadScheduledExecutor();
    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    @PostConstruct
    public void init() {
        log.info("Message publisher has been started...");
        scheduledPool.scheduleAtFixedRate(() -> {
            List<Message> messages = generator.generate();
            for (Message message : messages) {
                log.info("Message generated: {}", message);
                messageQueue.forEach((subscriber, mq) -> mq.add(message));
                fireMessage();
            }
        }, 10, 300, TimeUnit.SECONDS);
    }

    @Override
    public void fireMessage() {
        messageQueue.forEach(((subscriber, mq) -> CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return mq.take();
                    } catch (InterruptedException ie) {
                        log.error("Error occurred:", ie);
                    }
                    return null;
                }, threadPool)
                .thenAccept(subscriber::onMessage)));
    }

    @Override
    public void register(final ISubscriber subscriber) {
        messageQueue.putIfAbsent(subscriber, new PriorityBlockingQueue<>());
    }

    @Override
    public void unregister(final ISubscriber subscriber) {
        messageQueue.remove(subscriber);
    }
}
