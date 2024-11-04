package com.software.dto.event;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EventDto {

    String title;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime createTime;
}
