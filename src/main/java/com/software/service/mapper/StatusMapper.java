package com.software.service.mapper;

import com.software.domain.item.Status;
import com.software.dto.item.status.StatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ItemTypeMapper.class})
public interface StatusMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemType")
    @Named("toStatus")
    Status toStatus(StatusDto statusDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemTypeDto")
    @Named("toStatusDto")
    StatusDto toStatus(Status status);

}
