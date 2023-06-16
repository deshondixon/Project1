package com.revature.daos;

import com.revature.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);

    boolean existsByUsername(String username);
}
