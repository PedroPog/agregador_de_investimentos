package com.codehive.agregador.service;

import com.codehive.agregador.controller.dto.CreateUserDto;
import com.codehive.agregador.controller.dto.UpdateUserDto;
import com.codehive.agregador.entity.User;
import com.codehive.agregador.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create a user with success")
        void shoulCreateAUserWithSuccess(){
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("username","email@emai.com",
                    "123");
            var output = userService.createUser(input);

            assertNotNull(output);
            var userCapture = userArgumentCaptor.getValue();
            assertEquals(input.username(),userCapture.getUsername());
            assertEquals(input.email(),userCapture.getEmail());
            assertEquals(input.password(),userCapture.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto("username","email@emai.com",
                    "123");
            assertThrows(RuntimeException.class,()-> userService.createUser(input));
        }


    }

    @Nested
    class getUserById{


        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent(){

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getUserId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());

        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty(){

            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId,uuidArgumentCaptor.getValue());

        }
    }

    @Nested
    class listusers{


        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess(){

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();

            var output = userService.listUsers();

            assertNotNull(output);
            assertEquals(userList.size(),output.size());

        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty(){

            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId,uuidArgumentCaptor.getValue());

        }
    }

    @Nested
    class updateById{


        @Test
        @DisplayName("Should update user by id when user exists and username and password id filled")
        void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordFilled(){

            var updateUserDto = new UpdateUserDto(
                    "newUsername",
                    "newPassord"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.updateUserById(user.getUserId().toString(),updateUserDto);

            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(user.getUsername(),userCaptured.getUsername());
            assertEquals(user.getPassword(),userCaptured.getPassword());

            verify(userRepository,times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository,times(1))
                    .save(user);

        }

        @Test
        @DisplayName("Should not update user when user not exists")
        void shouldNotUpdateUserWhenUserNotExists(){

            var updateUserDto = new UpdateUserDto(
                    "newUsername",
                    "newPassord"
            );
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            userService.updateUserById(userId.toString(),updateUserDto);

            assertEquals(userId,uuidArgumentCaptor.getValue());

            verify(userRepository,times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository,times(0))
                    .save(any());

        }


    }

    @Nested
    class deleteById{


        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists(){

            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            userService.deleteById(userId.toString());

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId,idList.get(0));
            assertEquals(userId,idList.get(1));

            verify(userRepository,times(1)).existsById(idList.get(0));
            verify(userRepository,times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not delete user when user NOT exists")
        void shouldNotDeleteUserWhenUserNotExists(){

            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            userService.deleteById(userId.toString());

            assertEquals(userId,uuidArgumentCaptor.getValue());

            verify(userRepository,times(1))
                    .existsById(uuidArgumentCaptor.getValue());
            verify(userRepository,times(0))
                    .deleteById(any());
        }


    }
}