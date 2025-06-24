package com.automation.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class EmailNotificationEvent extends ApplicationEvent {

    private final String type;
    private final Map<String, String> message;

    public EmailNotificationEvent(Object source, String type, Map<String, String> message) {
        super(source);
        this.message = message;
        this.type = type;
    }

    public EmailNotificationEvent(Object source, Map<String, String> message) {
        super(source);
        this.message = message;
        this.type = "default";
    }

}