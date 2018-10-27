package org.alexside.gemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Alex on 27.10.2018.
 */
@Slf4j
@Component
public class Producer {
    @PostConstruct
    public void init() {
        log.info("HERE WE GO!");
    }
}
