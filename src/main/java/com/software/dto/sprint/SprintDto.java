package com.software.dto.sprint;

import com.software.dto.item.ItemListDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SprintDto {
    LocalDateTime startTime;
    LocalDateTime endTime;
    List<Long> command;
    ItemListDto items;
}
