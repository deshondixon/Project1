package com.revature.controllers;

import com.revature.daos.EmployeeDAO;
import com.revature.daos.PositionDAO;
import com.revature.dto.AuthResponseDTO;
import com.revature.dto.LoginDTO;
import com.revature.dto.RegisterDTO;
import com.revature.models.Employee;
import com.revature.models.Position;
import com.revature.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final EmployeeDAO employeeDAO;

    private final PositionDAO positionDAO;

    private final PasswordEncoder passwordEncoder;

    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, EmployeeDAO employeeDAO, PositionDAO positionDAO, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
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

        return new ResponseEntity<>("Account Registration was successful!", HttpStatus.CREATED);

    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<AuthResponseDTO>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}

