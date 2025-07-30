package org.task.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.task.domain.User;
import org.task.dto.UserDTO;
import org.task.repository.UserRepository;
import org.task.service.exception.EmailAlreadyExistsException;
import org.task.service.exception.InvalidPasswordException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateUserSuccessfully() {
        User user = new User("id123", "123456", "felipe@test.com", "Felipe");
        User userSaved = new User(null,"123456", "felipe@test.com", "Felipe");

        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        User result = userService.insert(user);

        assertNotNull(result);

        assertEquals(userSaved.getId(), result.getId());
        assertEquals(userSaved.getEmail(), result.getEmail());
        assertEquals(userSaved.getName(), result.getName());
        assertEquals(userSaved.getPassword(), result.getPassword());
    }

    @Test
    public void shouldThrowErrorWhenUserAlreadyExists() {
        User existingUser = new User("id123", "123456", "felipe@test.com", "Felipe");
        User newUser = new User(null, "123456", "felipe@test.com", "Felipe");

        when(userRepository.findByEmail("felipe@test.com")).thenReturn(existingUser);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.insert(newUser);
        });
    }

    @Test
    public void shouldFindUserById() {
        User existingUser = new User("id123", "123456", "felipe@test.com", "Felipe");

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        User result = userService.find(existingUser.getId());

        assertEquals("id123", result.getId());
        assertEquals("Felipe", result.getName());
        assertEquals("felipe@test.com", result.getEmail());
    }

    @Test
    public void shouldNotFindUserById() {
        when(userRepository.findById("123456789")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            userService.find("123456789");
        });
    }

    @Test
    public void shouldConvertToDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("teste@teste.com");
        userDTO.setName("teste");
        userDTO.setPassword("123456");

        User result = userService.fromDTO(userDTO);

        assertEquals("teste@teste.com", result.getEmail());
        assertEquals("teste", result.getName());
        assertNotEquals("123456", result.getPassword());
        assertNull(result.getId());
    }

    @Test
    public void shouldValidateMinPasswordValue(){
        User user = new User();
        user.setEmail("teste@teste.com");
        user.setName("teste");
        user.setPassword("12");

        assertThrows(InvalidPasswordException.class, () -> {
            userService.insert(user);
        });
    }
}
