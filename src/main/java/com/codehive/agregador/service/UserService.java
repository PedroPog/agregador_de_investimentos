package com.codehive.agregador.service;

import com.codehive.agregador.controller.CreateUserDto;
import com.codehive.agregador.controller.UpdateUserDto;
import com.codehive.agregador.entity.User;
import com.codehive.agregador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UUID createUser(CreateUserDto createUserDto){
        var entity = new User(
                null,
                createUserDto.username(), createUserDto.email(),
                createUserDto.password(), Instant.now(),null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUsuarioById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void updatedUserById(String userId,
                                UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            var user = userEntity.get();
            if(updateUserDto.username()!=null){
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password()!=null){
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);
        if(userExists){
            userRepository.deleteById(id);
        }
    }
}
