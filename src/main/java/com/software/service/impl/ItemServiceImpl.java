package com.software.service.impl;

import com.software.data.item.ItemRepository;
import com.software.domain.item.Item;
import com.software.service.ItemService;
import com.software.service.ProjectService;
import com.software.service.exception.item.ItemNotFoundException;
import com.software.service.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ProjectService projectService;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItems(Long projectId) {
        var project = projectService.getProjectById(projectId);
        return itemRepository.findByProject(project);
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.info("Item with id {} not found", itemId);
                    return new ItemNotFoundException(itemId);
                });
    }

    @Override
    @Transactional
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public Item updateItem(Long itemId, Item updatedItem) {
        var itemToUpdate = getItemById(itemId);

        itemMapper.updateItem(updatedItem, itemToUpdate);

        return itemRepository.save(itemToUpdate);
    }

    @Override
    @Transactional
    public void deleteItemById(Long itemId) {
        if (itemRepository.findById(itemId).isPresent()) {
            itemRepository.deleteById(itemId);

        } else {
            log.warn("Attempt to delete item with id {}", itemId);
        }
    }
}
