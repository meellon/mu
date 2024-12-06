package com.myproject.models.components;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Component
public class MessageComponent {
    // 환경 설정 으로 분리 필요
    private static final Locale DEFAULT_LOCALE = Locale.KOREA;

    public final MessageSource messageSource;

    public MessageComponent(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static MessageComponent MESSAGE;

    @PostConstruct
    private void init() { MESSAGE = this; }

    private String getSourceMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, DEFAULT_LOCALE);
    }
    public String getMessage(String code, Object... args) {
        return this.getSourceMessage(code, args);
    }
    public String getMessage(String code) {
        return this.getSourceMessage(code, null);
    }
}
