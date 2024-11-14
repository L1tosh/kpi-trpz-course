package com.software.service;

import com.software.domain.item.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();

    List<Item> getAllItems(Long projectId);

    Item getItemById(Long itemId);
    Item createItem(Item item);

    Item updateItem(Long itemId, Item updatedItem);

    void deleteItemById(Long itemId);

}
