package com.shaho.spring.primary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotifierTest {

    @Autowired Notifier defaultNotifier;                 // resolves to @Primary
    @Autowired @Qualifier("sms") Notifier smsNotifier;   // @Qualifier overrides @Primary

    @Test
    void primaryIsInjectedByDefault() {
        assertEquals("email:hi", defaultNotifier.send("hi"));
    }

    @Test
    void qualifierOverridesPrimary() {
        assertEquals("sms:hi", smsNotifier.send("hi"));
    }
}
