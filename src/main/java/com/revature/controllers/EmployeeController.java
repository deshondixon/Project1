package com.revature.controllers;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.security.JwtGenerator;
import com.revature.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    private final JwtGenerator jwtGenerator;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JwtGenerator jwtGenerator) { this.employeeService = employeeService;
        this.jwtGenerator = jwtGenerator;
    }

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

    // register
    @PostMapping("{eid}/reimbursements/register/{rid}")
    public ResponseEntity<?> registerForReimbursementHandler(@PathVariable("eid") int eid, @PathVariable("rid") int rid, @RequestHeader("Authorization") String bearerToken ){
        String username = jwtGenerator.getUsernameFromToken(bearerToken.substring(7));
        Employee e = employeeService.findEmployeeByUsername(username);

        if (e.getId() == eid){
            return new ResponseEntity<Employee>(employeeService.registerForReimbursement(eid, rid), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Cannot access another Employees Information!",HttpStatus.FORBIDDEN);
        }
    }

    public Employee registerForReimbursement(int employeeId, int reimbursementId) {
        Employee employee = getEmployeeById(employeeId);
        Reimbursement reimbursement = reimbursementService.findReimbursementById(reimbursementId);

        List<Reimbursement> reimbursements = employee.getReimbursements();

        if (!reimbursements.contains(reimbursement)) {
            reimbursements.add(reimbursement);
            employee.setReimbursements(reimbursements);
            employeeDAO.save(employee);
        }

        return employee;
    }

    private Employee getEmployeeById(int employeeId) {
    }


    // unregister
    @DeleteMapping("{eid}/reimbursements/unregister/{rid}")
    public Employee unregisterForReimbursementHandler(@PathVariable("eid") int eid, @PathVariable("rid") int rid){
        return employeeService.unregisterForReimbursement(eid, rid);
    }


}


