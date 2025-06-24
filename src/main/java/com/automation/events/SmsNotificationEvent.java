package com.automation.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class SmsNotificationEvent extends ApplicationEvent {
    private final Map<String, String> message;

    public SmsNotificationEvent(Object source, Map<String, String> message) {
        super(source);
        this.message = message;
    }
}
