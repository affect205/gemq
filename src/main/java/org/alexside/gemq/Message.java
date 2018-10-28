package org.alexside.gemq;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Message implements Comparable<Message> {
    private final UUID id;
    private final Instant time;
    private final int priority;
    private final String content;
    @Override
    public int compareTo(Message o) {
        if (priority > o.getPriority()) {
            return 1;
        } else if (priority < o.getPriority()) {
            return -1;
        }
        return time.compareTo(o.getTime());
    }
    @Override
    public String toString() {
        return String.format("ID: %s, TIME: %s, PRIORITY: %s\n%s", id, time, priority, content);
    }
}
