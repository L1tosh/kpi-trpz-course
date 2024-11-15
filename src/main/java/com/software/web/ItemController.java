package com.software.web;

import com.software.annotation.CheckUserInProject;
import com.software.dto.item.ItemEntry;
import com.software.dto.item.ItemListDto;
import com.software.service.ItemService;
import com.software.service.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @CheckUserInProject
    @GetMapping("/{id}")
    public ResponseEntity<ItemEntry> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemMapper.toItemEntry(itemService.getItemById(id)));
    }

    @CheckUserInProject
    @GetMapping
    public ResponseEntity<ItemListDto> getAllItems(@RequestParam Long projectId) {
        return ResponseEntity.ok(itemMapper.toItemListDto(itemService.getAllItems(projectId)));
    }

}
