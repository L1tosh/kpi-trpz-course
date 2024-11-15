package com.software.service.mapper;

import com.software.domain.item.Item;
import com.software.dto.item.ItemDto;
import com.software.dto.item.ItemEntry;
import com.software.dto.item.ItemListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemTypeMapper.class, StatusMapper.class})
public interface ItemMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatus")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemType")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    Item toItem(ItemDto itemDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatus")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemType")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    Item toItem(ItemEntry itemEntry);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusDto")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemTypeDto")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    ItemDto toItemDto(Item item);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusDto")
    @Mapping(source = "itemType", target = "itemType", qualifiedByName = "toItemTypeDto")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    ItemEntry toItemEntry(Item item);

    List<ItemDto> toItemDto(List<Item> itemList);
    List<Item> toItem(List<ItemDto> itemDtoList);

    @Named("toItemListDto")
    default ItemListDto toItemListDto(List<Item> itemList) {
        if (itemList == null) return null;

        return ItemListDto.builder().itemDtos(toItemDto(itemList)).build();
    }

    @Named("toItemList")
    default List<Item> toItemList(ItemListDto itemListDto) {
        return toItem(itemListDto.getItemDtos());
    }

    void updateItem(Item source, @MappingTarget Item target);
}
