package org.alexside.gemq;

public interface ISubscriber {
    String getSid();
    IPublisher.OffsetMode getMode();
    void onMessage(Message message);
    void start();
    void stop();
}
