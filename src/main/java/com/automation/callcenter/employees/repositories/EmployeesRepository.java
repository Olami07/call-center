package com.automation.callcenter.employees.repositories;

import com.automation.callcenter.employees.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    @Query("SELECT s FROM Employees s WHERE s.avayaId = ?1")
    Employees findEmployeeByAvayaId(Long avayaId);

    Employees findEmployeesByAvayaId(Long avayaId);

    Employees findEmployeeByStaffIdAndUsername(String staffId, String username);

    Employees findEmployeesBystaffId(String staffId);

    Employees findEmployeesByUsername(String staffId);

    Employees findEmployeeByAvayaIdAndStaffIdAndUsername(Long avayaId, String staffId, String username);

    Employees findEmployeeByAvayaIdAndStaffId(Long avayaId, String staffId);

    Employees findEmployeeByAvayaIdAndUsername(Long avayaId, String username);

    List<Employees> findByTeam(String team);

    boolean existsByTeam(String team);
}
