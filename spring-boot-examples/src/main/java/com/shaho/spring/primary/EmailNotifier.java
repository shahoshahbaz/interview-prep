package com.shaho.spring.primary;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/** @Primary wins when several beans match by type and no qualifier is given. */
@Component
@Primary
public class EmailNotifier implements Notifier {
    @Override
    public String send(String message) {
        return "email:" + message;
    }
}
