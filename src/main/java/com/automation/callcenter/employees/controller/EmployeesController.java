package com.automation.callcenter.employees.controller;

import com.automation.callcenter.employees.entity.Employees;
import com.automation.callcenter.employees.service.EmployeesService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("employees-api")
public class EmployeesController {
    private EmployeesService employeesService;
    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping(path = "/add-employee")
    public ResponseEntity<Employees> addEmployee(@RequestBody Employees employee) {
        Employees createdEmployee = employeesService.saveEmployee(employee, "admin");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
//        return employeesService.saveEmployee(employee);
    }

    //Post bulk employees
    @PostMapping(path = "/add-employees")
    public List<Employees> addEmployees(@RequestBody List<Employees> employees) {
        return employeesService.saveEmployees(employees);
    }

    //Getting employee by either AvayaId, StaffId, username, AvayaId&StaffId&Username, AvayaId&StaffId, AvayaId&Username, StaffId&Username
    @GetMapping("/get-employee")
    public ResponseEntity<Object> getEmployeeByAvayaId(
            @RequestParam(required = false) Long avayaId,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String username) {
        try {
            return employeesService.getEmployeeByAvayaId(avayaId, staffId, username);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/get-employees")
    public List<Employees> findAllEmployees() {
        return employeesService.getEmployees();
    }
    //Getting employee by either Team,
    @GetMapping("/get-employees/{team}")
    public ResponseEntity<Object> getEmployeesByTeam(@PathVariable() String team) {
        try {
            if (team == null) {
                List<Employees> employees = employeesService.getEmployees();
                return ResponseEntity.ok().body(employees);
            } else {
                List<Employees> employees = employeesService.getEmployeesByTeam(team);
                if (employees.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Record not found for Team: " + team + "\"}");
                } else {
                    return ResponseEntity.ok().body(employees);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while fetching data\"}");
        }
    }


    @PutMapping("/update-employee/{avayaId}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long avayaId, @RequestBody Employees updatedEmployee) {
        try {
            Employees existingEmployee = employeesService.getEmployeeAvayaId(avayaId);
            if (existingEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found with Avaya ID: " + avayaId + "\"}");
            }
            // Update the existing employee with the new data
            existingEmployee.setStaffId(updatedEmployee.getStaffId());
            existingEmployee.setFirst_name(updatedEmployee.getFirst_name());
            existingEmployee.setLast_name(updatedEmployee.getLast_name());
            existingEmployee.setUsername(updatedEmployee.getUsername());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setTeam(updatedEmployee.getTeam());
            existingEmployee.setLanguage(updatedEmployee.getLanguage());
            existingEmployee.setStatus(updatedEmployee.getStatus());

            // Save the updated employee
            employeesService.updateEmployee(existingEmployee);

            return ResponseEntity.ok().body(existingEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while updating employee\"}");
        }
    }

    // DeleteMapping for deleting an employee by Avaya ID
    @DeleteMapping("/delete-employee/{avayaId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long avayaId) {
        try {
            Employees existingEmployee = employeesService.getEmployeeAvayaId(avayaId);
            if (existingEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found with Avaya ID: " + avayaId + "\"}");
            }

            // Delete the employee
            employeesService.deleteEmployee(existingEmployee);

            return ResponseEntity.ok().body("{\"message\": \"Employee with Avaya ID " + avayaId + " has been deleted\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while deleting employee\"}");
        }
    }
}







