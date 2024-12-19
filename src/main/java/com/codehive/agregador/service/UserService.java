package com.codehive.agregador.service;

import com.codehive.agregador.controller.dto.AccountResponseDto;
import com.codehive.agregador.controller.dto.CreateAccountDto;
import com.codehive.agregador.controller.dto.CreateUserDto;
import com.codehive.agregador.controller.dto.UpdateUserDto;
import com.codehive.agregador.entity.Account;
import com.codehive.agregador.entity.BillingAddress;
import com.codehive.agregador.entity.User;
import com.codehive.agregador.repository.AccountRepository;
import com.codehive.agregador.repository.BillingAddressRepository;
import com.codehive.agregador.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepositoy;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepositoy,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepositoy = accountRepositoy;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        // DTO -> ENTITY
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }

    }

    public void deleteById(String userId) {

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        if (isNull(user.getAccounts())) {
            user.setAccounts(new ArrayList<>());
        }

        // DTO -> ENTITY
        var account = new Account(
            UUID.randomUUID(),
            user,
            null,
            createAccountDto.description(),
            new ArrayList<>()
        );

        var accountCreated = accountRepositoy.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                accountCreated,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    public List<Account> findAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        return user.getAccounts();
    }

    public List<AccountResponseDto> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(),ac.getDescription()))
                .toList();

    }
}
