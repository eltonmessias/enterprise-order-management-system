package com.eltonmessias.inventoryservice.inventory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody @Valid InventoryRequest request) {
        return new ResponseEntity<>(inventoryService.createInventory(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<InventoryResponse>> getAllInventories(
            @PageableDefault(size = 20, sort = "productId") Pageable pageable
    ) {
        PagedResponse<InventoryResponse> inventories = inventoryService.getAllInventories(pageable);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{inventory-id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable("inventory-id") UUID inventoryId) {
        return new ResponseEntity<>(inventoryService.getInventoryById(inventoryId), HttpStatus.OK);
    }


}
