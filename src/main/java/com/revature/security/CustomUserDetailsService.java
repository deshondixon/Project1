package com.revature.security;

import com.revature.daos.EmployeeDAO;
import com.revature.models.Employee;
import com.revature.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public CustomUserDetailsService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee e = employeeDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No Employee found"));
        return new User(e.getUsername(), e.getPassword(), mapPositionToAuthority(e.getPosition()));
    }

    private Collection<GrantedAuthority> mapPositionToAuthority(Position position){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(position.getName()));
        return grantedAuthorities;
    }
}
