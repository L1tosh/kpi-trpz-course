package com.software.dto.item.status;

import com.software.dto.item.type.ItemTypeDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatusDto {

     String name;
     ItemTypeDto itemType;
}
