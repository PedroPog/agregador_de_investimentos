package com.codehive.agregador.controller;

import com.codehive.agregador.controller.dto.CreateAccountDto;
import com.codehive.agregador.controller.dto.CreateUserDto;
import com.codehive.agregador.controller.dto.UpdateUserDto;
import com.codehive.agregador.entity.User;
import com.codehive.agregador.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){
        var userID = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/"+userID.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUsuarioById(userId);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
        //return user.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    private ResponseEntity<Void> updateUser(@PathVariable("userId") String userId,
                                            @RequestBody UpdateUserDto updateUserDto){
        userService.updatedUserById(userId,updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userID){
        userService.deleteById(userID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                           @RequestBody CreateAccountDto createAccountDto){
        userService.createAccount(userId,createAccountDto);
        return ResponseEntity.ok().build();
    }
}
