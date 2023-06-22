package com.revature.services;

import com.revature.daos.EmployeeDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO, ReimbursementDAO reimbursementDAO) {
        this.employeeDAO = employeeDAO;
        this.reimbursementDAO = reimbursementDAO;
    }

    //GET-ALL
    public List<Employee> getAllEmployee() {
        return employeeDAO.findAll();
    }

    //GET BY ID
    public Employee getEmployeeById(int id) {
        return employeeDAO.findById(id).orElseThrow(() -> new EmployeeNotFoundException("No employee found with id: " + id));
    }

    public Employee findEmployeeByUsername(String username){
        return employeeDAO.findByUsername(username).orElseThrow(() -> new EmployeeNotFoundException("No employee found with username" + username));
    }

    //INSERT
    public Employee createEmployee(Employee emp) {
        Employee returnedEmployee = employeeDAO.save(emp);

        if (returnedEmployee.getId() > 0) {
            //ADD
        } else {
            //ADD
        }

        return returnedEmployee;
    }

    //UPDATE
    public Employee updateEmployee(Employee emp) {
        return employeeDAO.save(emp);
    }

    //DELETE
    public boolean deleteEmployeeById(int id) {
        employeeDAO.deleteById(id);

        if (!employeeDAO.existsById(id)) {
            System.out.println("Employee Deleted Successfully!");
            return true;
        } else {
            return false;
        }
    }

    ////// Reimbursement registered to Employee
    public List<Reimbursement> getReimbursementByEmployeeId(int id) {
        Optional<Employee> returnedEmployee = employeeDAO.findById(id);
        if (returnedEmployee.isPresent()) {
            return returnedEmployee.get().getReimbursements();
        } else {
            throw new EmployeeNotFoundException("No Employee with id: " + id);
        }
    }

    //register
    public Employee registerForReimbursement(int eid, int rid) {
        Employee e = getEmployeeById(eid);

        List<Reimbursement> reimbursements = e.getReimbursements();
        Optional<Reimbursement> returnedReimbursement = reimbursementDAO.findById(rid);

        if (returnedReimbursement.isPresent()) {
            if (!reimbursements.contains(returnedReimbursement.get())) {

                reimbursements.add(returnedReimbursement.get());
                e.setReimbursements(reimbursements);
                employeeDAO.save(e);
            }
        } else {
            throw new ReimbursementNotFoundException("No Reimbursement with id: " + rid);
        }
        return e;
    }

    public Employee unregisterForReimbursement(int eid, int rid) {
        Employee e = getEmployeeById(eid);

        List<Reimbursement> reimbursements = e.getReimbursements();

        Optional<Reimbursement> returnedReimbursement = reimbursementDAO.findById(rid);

        if (returnedReimbursement.isPresent() && reimbursements.contains(returnedReimbursement.get())) {
            reimbursements.remove(returnedReimbursement.get());
            e.setReimbursements(reimbursements);
            employeeDAO.save(e);
        } else {
            throw new ReimbursementNotFoundException("No reimbursement with id: " + eid);
        }
        return e;

    }
}
