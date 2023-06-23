package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final EmployeeService employeeService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, EmployeeService employeeService) {
        this.reimbursementService = reimbursementService;
        this.employeeService = employeeService;
    }


    //GET-ALL
    @GetMapping
    public List<Reimbursement> getAllReimbursementHandler(@RequestParam(name="search", required = false) String searchPattern){
        if(searchPattern != null){
            return reimbursementService.searchReimbursements(searchPattern);
        }
        return reimbursementService.getAllReimbursements();
    }


    //GET BY ID
    @GetMapping({"id"})
    public Reimbursement findReimbursementByIdHandler(@PathVariable("id") int id){
        return reimbursementService.findReimbursementById(id);
    }

    //INSERT
    @PostMapping
    public Reimbursement createReimbursementHandler(@RequestBody Reimbursement r){
        if (r.getStatus() == null) {
            r.setStatus("Pending");
        }
        return reimbursementService.addReimbursement(r);
    }

    //UPDATE
    @PutMapping
    public Reimbursement updateReimbursementHandler(@RequestBody Reimbursement r){
        return reimbursementService.updateReimbursement(r);
    }

    //DELETE
    @DeleteMapping("delete/{id}")
    public boolean deleteReimbursementHandler(@PathVariable("id") int id){
        return reimbursementService.deleteReimbursement(id);
    }


}
