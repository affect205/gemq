package org.alexside.gemq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.alexside.gemq.IPublisher.OffsetMode.NEWEST;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	@Autowired
	private IPublisher publisher;

	@Test
	public void publisherTest() throws Exception {
		final String sid1 = "subscriber1";
		ISubscriber subscriber1 = new Subscriber(publisher, sid1, NEWEST, (msg) -> {
			log.info("[{}]Message received: {}", sid1, msg);
		});
		final String sid2 = "subscriber2";
		ISubscriber subscriber2 = new Subscriber(publisher, sid2, NEWEST, (msg) -> {
			log.info("[{}]Message received: {}", sid2, msg);
		});
		subscriber1.start();
		subscriber2.start();
		Thread.sleep(100000);
		subscriber1.stop();
		subscriber2.stop();
	}
}
