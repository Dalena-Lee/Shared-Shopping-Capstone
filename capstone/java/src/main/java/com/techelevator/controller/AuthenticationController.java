package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.dao.JdbcUserDao;
import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.techelevator.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        
        User user = userDao.findByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        try {
            User user = userDao.findByUsername(newUser.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        } catch (UsernameNotFoundException e) {
            userDao.create(newUser.getUsername(),newUser.getPassword(), newUser.getRole());
        }
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@Valid @PathVariable int id){
        User user;
        try {
        user = userDao.getUserById(id);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found.");
        }
        return user;
    }


    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> findAll(){
        List user;
        try {
            user = userDao.findAll();
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server is down for maintnence.");
        }
        return user;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername(@Valid @PathVariable String username){
        User user;
        try {
            user = userDao.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public boolean create(@Valid @PathVariable String username, @Valid @PathVariable String password, @Valid @PathVariable String role){
        return userDao.create(username, password, role);
    }

}

//@ResponseStatus(HttpStatus.FOUND)
//@RequestMapping(value = "/users", method = RequestMethod.GET)
//public (@Valid @PathVariable ){
//        User user;
//        try {
//        user = userDao.;
//        } catch (UsernameNotFoundException e) {
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
//        }
//        return user;
//        }