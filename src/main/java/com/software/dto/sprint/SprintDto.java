package com.software.dto.sprint;

import com.software.dto.item.ItemListDto;
import com.software.dto.user.UserListDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class SprintDto {
    LocalDateTime startTime;
    LocalDateTime endTime;
    UserListDto command;
    ItemListDto items;
}
