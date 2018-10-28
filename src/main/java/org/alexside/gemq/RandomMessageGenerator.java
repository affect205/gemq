package org.alexside.gemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Component
public class RandomMessageGenerator implements IMessageGenerator {
    @Override
    public List<Message> generate() {
        List<Message> messages = new ArrayList<>();
        int count = current().nextInt(20, 100);
        log.info("Generate {} messages", count);
        IntStream.range(0, count).forEach(ndx -> {
            String content = randomString(current().nextInt(1, 100));
            int priority = current().nextInt(1, 8);
            messages.add(new Message(UUID.randomUUID(), Instant.now(), priority, content));

        });
        return messages;
    }

    private String randomString(int length) {
        byte[] array = new byte[length];
        current().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
