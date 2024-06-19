package com.automation.callcenter.units.repository;

import com.automation.callcenter.units.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Units, Long> {
}