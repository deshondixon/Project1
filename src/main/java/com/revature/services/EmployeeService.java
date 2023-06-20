package com.revature.services;

import com.revature.daos.EmployeeDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO, ReimbursementDAO reimbursementDAO){
        this.employeeDAO = employeeDAO;
        this.reimbursementDAO = reimbursementDAO;
    }
    //GET-ALL
    public List<Employee> getAllEmployee() { return employeeDAO.findAll();}

    //GET BY ID
    public Employee getEmployeeById(int id){
        return employeeDAO.findById(id).orElseThrow(() -> new EmployeeNotFoundException("No employee found with id: " + id));
    }

    //INSERT
    public Employee createEmployee(Employee emp){
        Employee returnedEmployee = employeeDAO.save(emp);

        if (returnedEmployee.getId() > 0){
            //ADD
        } else{
            //ADD
        }

        return returnedEmployee;
    }

    //UPDATE
    public Employee updateEmployee(Employee emp){ return employeeDAO.save(emp);}

    //DELETE
    public boolean deleteEmployeeById(int id){
        employeeDAO.deleteById(id);

        if(!employeeDAO.existsById(id)){
            System.out.println("Employee Deleted Successfully!");
            return true;
        } else {
            return false;
        }
    }
}
