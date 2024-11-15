package com.software.service.impl;

import com.software.data.ProjectRepository;
import com.software.data.item.ItemRepository;
import com.software.domain.item.Item;
import com.software.service.ItemService;
import com.software.service.exception.item.ItemNotFoundException;
import com.software.service.exception.project.ProjectNotFoundException;
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
    private final ProjectRepository projectRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItems(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(() -> {
                    log.info("Project with if {} not found", projectId);
                    return new ProjectNotFoundException(projectId);
                }
        );
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
