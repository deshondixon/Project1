package com.revature.controllers;

import com.revature.daos.EmployeeDAO;
import com.revature.daos.PositionDAO;
import com.revature.dto.RegisterDTO;
import com.revature.models.Employee;
import com.revature.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final EmployeeDAO employeeDAO;

    private final PositionDAO positionDAO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, EmployeeDAO employeeDAO, PositionDAO positionDAO, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if (employeeDAO.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<String>("Username already in use.", HttpStatus.BAD_REQUEST);
        }
        Employee e = new Employee();
        e.setFirstName(registerDTO.getFirstName());
        e.setLastName(registerDTO.getLastName());
        e.setUsername(registerDTO.getUsername());
        e.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Position position = positionDAO.getByName("Employee");
        e.setPosition(position);
        employeeDAO.save(e);

        return new ResponseEntity<>("Registration was successful!", HttpStatus.CREATED);

    }
}

