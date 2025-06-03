package com.bankkata.infrastructure;

import com.bankkata.domain.ClockService;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class SystemClockService implements ClockService {

    private final Clock clock;

    public SystemClockService() {
        this.clock = Clock.systemDefaultZone();
    }

    public SystemClockService(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LocalDate now() {
        return LocalDate.now(clock);
    }
}