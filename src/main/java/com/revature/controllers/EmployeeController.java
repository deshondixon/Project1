package com.revature.controllers;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.security.JwtGenerator;
import com.revature.services.EmployeeService;
import com.revature.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Configuration
@ComponentScan("com.revature.services")
@RequestMapping("employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ReimbursementService reimbursementService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ReimbursementService reimbursementService, JwtGenerator jwtGenerator) {
        this.employeeService = employeeService;
        this.reimbursementService = reimbursementService;
        this.jwtGenerator = jwtGenerator;
    }

    // GET-ALL
    @GetMapping
    public List<Employee> getAllEmployeeHandler() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("{id}")
    public Employee getEmployeeByIdHandler(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    // INSERT
    @PostMapping
    public ResponseEntity<?> createEmployeeHandler(@RequestBody Employee employee) {
        String username = employee.getUsername();

        if (usernameIsRegistered(username)) {
            return new ResponseEntity<>("Username is already registered!", HttpStatus.CONFLICT);
        }

        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping
    public Employee updateEmployeeHandler(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    // DELETE
    @DeleteMapping("{id}")
    public boolean deleteEmployeeHandler(@PathVariable("id") int id) {
        return employeeService.deleteEmployeeById(id);
    }

    @GetMapping("{id}/reimbursements")
    public List<Reimbursement> getReimbursementsEmployeeIdHandler(@PathVariable("id") int id) {
        return employeeService.getReimbursementByEmployeeId(id);
    }

    @PostMapping("reimbursements")
    public ResponseEntity<?> createReimbursementHandler(@RequestBody Reimbursement reimbursement, @RequestHeader("Authorization") String bearerToken) {
        String username = jwtGenerator.getUsernameFromToken(bearerToken.substring(7));
        Employee employee = employeeService.findEmployeeByUsername(username);

        if (employee != null) {
            reimbursement.setEmployee(employee);
            Reimbursement createdReimbursement = reimbursementService.addReimbursement(reimbursement);
            return new ResponseEntity<>(createdReimbursement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot create reimbursement for another employee.", HttpStatus.FORBIDDEN);
        }
    }

    private boolean usernameIsRegistered(String username) {
        Employee employee = employeeService.findEmployeeByUsername(username);
        return employee != null;
    }


//    // Unregister
//    @DeleteMapping("{eid}/reimbursements/unregister/{rid}")
//    public Employee unregisterForReimbursementHandler(@PathVariable("eid") int eid, @PathVariable("rid") int rid) {
//        return employeeService.unregisterForReimbursement(eid, rid);
//    }
}
