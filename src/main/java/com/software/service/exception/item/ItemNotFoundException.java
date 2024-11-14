package com.software.service.exception.item;

public class ItemNotFoundException extends RuntimeException {
    private static final String ITEM_NOT_FOUND_MESSAGE = "Item with %d not found";

    public ItemNotFoundException(Long itemId) {
        super(ITEM_NOT_FOUND_MESSAGE.formatted(itemId));
    }

}
