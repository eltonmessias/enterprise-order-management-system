package com.eltonmessias.inventoryservice.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/oms/inventory/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<UUID> createWarehouse(@RequestBody WarehouseRequest request) {
        return new ResponseEntity<>(warehouseService.createWarehouse(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        return new ResponseEntity<>(warehouseService.getAllWarehouses(), HttpStatus.OK);
    }

    @GetMapping("/{warehouse-id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable("warehouse-id") UUID warehouseId) {
        return new ResponseEntity<>(warehouseService.getWarehouseById(warehouseId), HttpStatus.OK);
    }
}
