package com.helmes.task.rest.controllers;

import com.helmes.task.config.AppConstants;
import com.helmes.task.exception.AppException;
import com.helmes.task.persistence.model.User;
import com.helmes.task.rest.dto.UserAddDTO;
import com.helmes.task.rest.dto.UserUpdateDTO;
import com.helmes.task.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin(origins = AppConstants.CLIENT_URL_DEV)
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserAddDTO user) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateDTO user) throws AppException {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok().body(userService.findUserByName(name));
    }
}
