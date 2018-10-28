package org.alexside.gemq;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"sid"})
@ToString(of = {"sid"})
public class Subscriber implements ISubscriber {
    private final IPublisher publisher;
    @Getter private final String sid;
    @Getter private final IPublisher.OffsetMode mode;
    private final Consumer<Message> callback;
    @Override
    public synchronized void start() {
        log.info("Start publisher {}", sid);
        publisher.register(this);
    }
    @Override
    public synchronized void stop() {
        log.info("Stop publisher {}", sid);
        publisher.unregister(this);
    }
    @Override
    public synchronized void onMessage(Message message) {
        try {
            callback.accept(message);
            Thread.sleep(1000L);
        } catch (InterruptedException ie) {
            log.error("Error occurred:", ie);
        }
    }
}
