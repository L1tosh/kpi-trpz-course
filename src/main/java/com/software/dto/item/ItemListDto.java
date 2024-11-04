package com.software.dto.item;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ItemListDto {
    List<ItemDto> itemDtos;
}
