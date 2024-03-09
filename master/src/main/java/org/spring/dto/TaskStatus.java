package org.spring.dto;


import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
public enum TaskStatus {
    WAITING(1),
    RUNNING(2),
    FINISH(3),
    FAIL(4),
    ABANDON(5);

    private final int statusCode;

    TaskStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}


