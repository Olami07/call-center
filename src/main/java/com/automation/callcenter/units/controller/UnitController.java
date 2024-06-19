package com.automation.callcenter.units.controller;

import com.automation.callcenter.units.entity.Units;
import com.automation.callcenter.units.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<List<Units>> getAllUnits() {
        List<Units> units = unitService.getAllUnits();
        return ResponseEntity.ok().body(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Units> getUnitById(@PathVariable Long id) {
        Units unit = unitService.getUnitById(id);
        if (unit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(unit);
    }

    @PostMapping
    public ResponseEntity<Units> createUnit(@RequestBody Units unit) {
        Units createdUnit = unitService.createUnit(unit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Units> updateUnit(@PathVariable Long id, @RequestBody Units updatedUnit) {
        Units unit = unitService.updateUnit(id, updatedUnit);
        if (unit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(unit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        boolean deleted = unitService.deleteUnit(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}