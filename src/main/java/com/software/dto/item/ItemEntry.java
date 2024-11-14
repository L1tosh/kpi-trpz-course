package com.software.dto.item;

import com.software.dto.item.status.StatusDto;
import com.software.dto.item.type.ItemTypeDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ItemEntry {
    Long id;
    String title;
    String description;
    Integer complexity;

    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime createTime;

    StatusDto status;
    ItemTypeDto itemType;

    Long author;
    Long executor;
}
