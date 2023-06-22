package com.revature.controllers;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService;}

    //GET-ALL
    @GetMapping
    public List<Employee> getAllEmployeeHandler(){ return employeeService.getAllEmployee();}

    //INSERT
    @PostMapping
    public Employee createEmployeeHandler(@RequestBody Employee emp) { return employeeService.createEmployee(emp);}

    //UPDATE
    @PutMapping
    public Employee updateEmployeeHandler(@RequestBody Employee emp) { return employeeService.updateEmployee(emp);}

    //DELETE
    @DeleteMapping("{id}")
    public boolean deleteEmployeeHandler(@PathVariable("id") int id){
        return employeeService.deleteEmployeeById(id);
    }


    @GetMapping("{id}/reimbursements")
    public List<Reimbursement> getReimbursementsFromReimbursementHandler(@PathVariable("id") int id){
        return employeeService.getReimbursementByEmployeeId(id);
    }

    @PostMapping("reimbursements/{rid}/register/{eid}")
    public Employee registerForReimbursementHandler(@PathVariable("eid") int eid, @PathVariable("rid") int rid){
        return employeeService.registerForReimbursement(eid, rid);
    }

    @DeleteMapping("reimbursements/{rid}/register/{eid}")
    public Employee unregisterForReimbursementHandler(@PathVariable("rid") int rid, @PathVariable("eid") int eid){
        return employeeService.unregisterForReimbursement(eid, rid);
    }
}


