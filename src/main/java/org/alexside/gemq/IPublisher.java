package org.alexside.gemq;

public interface IPublisher {
    void register(final ISubscriber subscriber);
    void unregister(final ISubscriber subscriber);
    void fireMessage();
    enum OffsetMode {
        ORIGIN, NEWEST
    }
}
