package com.myproject.models.components;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MessageTestComponent {
    private final MessageComponent messageComponent;

    public MessageTestComponent(MessageComponent messageComponent) {
        this.messageComponent = messageComponent;
    }

    @PostConstruct
    public void testMessages() {
        String message = messageComponent.getMessage("exception.identity.found");
        System.out.println("Loaded message: " + message);
    }
}
