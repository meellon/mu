package com.myproject.models.domains.enumerations;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum CoordinationCategory {
    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리");

    private final String name;

    CoordinationCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CoordinationCategory of(String name) {
        return Arrays.stream(values())
                .filter(category -> category.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Unknown category: " + name));
    }
}
