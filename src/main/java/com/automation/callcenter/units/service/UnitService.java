package com.automation.callcenter.units.service;

import com.automation.callcenter.units.entity.Units;
import com.automation.callcenter.units.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Units> getAllUnits() {
        return unitRepository.findAll();
    }

    public Units getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    public Units createUnit(Units unit) {
        return unitRepository.save(unit);
    }

    public Units updateUnit(Long id, Units updatedUnit) {
        Units unit = unitRepository.findById(id).orElse(null);
        if (unit != null) {
            unit.setUnitName(updatedUnit.getUnitName());
            unit.setUnitCode(updatedUnit.getUnitCode());
            unit.setCreatedBy(updatedUnit.getCreatedBy());
            unit.setModifiedBy(updatedUnit.getModifiedBy());
            unit.setCreated(updatedUnit.getCreated());
            unit.setModified(updatedUnit.getModified());
            return unitRepository.save(unit);
        }
        return null;
    }

    public boolean deleteUnit(Long id) {
        if (unitRepository.existsById(id)) {
            unitRepository.deleteById(id);
            return true;
        }
        return false;
    }
}