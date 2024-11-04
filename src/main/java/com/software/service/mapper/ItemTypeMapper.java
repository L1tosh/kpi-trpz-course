package com.software.service.mapper;

import com.software.domain.item.ItemType;
import com.software.dto.item.type.ItemTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ItemTypeMapper {

    @Mapping(source = "name", target = "name")
    @Named("toItemTypeDto")
    ItemTypeDto toItemTypeDto(ItemType itemType);

    @Mapping(source = "name", target = "name")
    @Named("toItemType")
    ItemType toItemType(ItemTypeDto itemTypeDto);
}
