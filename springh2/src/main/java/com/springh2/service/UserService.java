package com.springh2.service;

import com.springh2.model.Users;
import com.springh2.repository.sys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Transactional
    public void register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public String verify(Users users) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
        );

        if(authentication.isAuthenticated())
            return jwtService.generateToken(users.getUsername());

        return "fail";
    }
}
