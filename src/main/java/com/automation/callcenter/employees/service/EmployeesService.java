package com.automation.callcenter.employees.service;

import com.automation.callcenter.employees.entity.Employees;
import com.automation.callcenter.employees.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }
    // Creating an individual Employee.
    public Employees saveEmployee(Employees employee, String createdBy) {
        Optional<Employees> employeesOptional = Optional.ofNullable(employeesRepository
                .findEmployeeByAvayaId(employee.getAvayaId()));
        if (employeesOptional.isPresent()) {
            throw new IllegalStateException("Employee AvayaId has been taken");
        }
        employee.setCreatedBy(createdBy);
        employee.setCreated(new Date());
        return employeesRepository.save(employee);
    }

    // Creating multiple employees
    public List<Employees> saveEmployees(List<Employees> employees) {

        return employeesRepository.saveAll(employees);
    }


    //Getting employee by either AvayaId, StaffId, username, AvayaId&StaffId&Username, AvayaId&StaffId, AvayaId&Username, StaffId&Username
    public ResponseEntity<Object> getEmployeeByAvayaId(Long avayaId, String staffId, String username) {
        if (avayaId == null && staffId == null && username == null) {
            return ResponseEntity.badRequest().body("{\"error\": \"At least one of avayaId, staffId, or username must be provided\"}");
        }

        Employees employee = null;
        if (avayaId != null && staffId == null && username == null ) {
            employee = employeesRepository.findEmployeesByAvayaId(avayaId);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Avaya ID: " + avayaId + "\"}");
            }
        } else if (avayaId == null && staffId != null && username == null) {
            employee = employeesRepository.findEmployeesBystaffId(staffId);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Staff ID: " + staffId + "\"}");
            }
        }else if (avayaId == null && staffId == null && username != null) {
            employee = employeesRepository.findEmployeesByUsername(username);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Username: " + username + "\"}");
            }

        } else if (avayaId == null && staffId != null && username != null) {
            employee = employeesRepository.findEmployeeByStaffIdAndUsername(staffId, username);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Staff ID: " + staffId + " and Username: " + username + "\"}");
            }

        } else if (avayaId != null && staffId != null && username == null) {
            employee = employeesRepository.findEmployeeByAvayaIdAndStaffId(avayaId, staffId);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Avaya ID: " + avayaId + " and Staff Id: " + staffId + "\"}");
            }

        } else if (avayaId != null && staffId == null && username != null) {
            employee = employeesRepository.findEmployeeByAvayaIdAndUsername(avayaId, username);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Avaya ID: " + avayaId + " and Username: " + username + "\"}");
            }

        } else if (avayaId != null && staffId != null && username != null) {
            employee = employeesRepository.findEmployeeByAvayaIdAndStaffIdAndUsername(avayaId, staffId, username);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Employee not found for Avaya ID: " + avayaId +  " and Staff ID: " + staffId + " and Username: " + username + "\"}");
            }
        }
        else {
            // Handle the case where insufficient parameters are provided
            return ResponseEntity.badRequest().body("{\"error\": \"Insufficient parameters provided\"}");
        }

        return ResponseEntity.ok().body(employee);
    }

    public List<Employees> getEmployees() {
        return employeesRepository.findAll();
    }

    public List<Employees> getEmployeesByTeam(String team) {

        return employeesRepository.findByTeam(team);
    }

    public Employees getEmployeeAvayaId(Long avayaId) {
        return employeesRepository.findEmployeeByAvayaId(avayaId);
    }

    public void updateEmployee(Employees existingEmployee, String modifiedBy) {
        existingEmployee.setModifiedBy(modifiedBy);
        existingEmployee.setModified(new Date());
        employeesRepository.save(existingEmployee);
    }

    // Method to delete an employee
    public void deleteEmployee(Employees employee) {
        employeesRepository.delete(employee);
    }

    public void updateEmployee(Employees existingEmployee) {

    }
//


}
