package org.task.service;

import org.task.domain.User;
import org.task.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.task.repository.UserRepository;
import org.task.service.exception.EmailAlreadyExistsException;
import org.task.service.exception.InvalidPasswordException;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User find(String id){

        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    @Transactional
    public User insert(User user) {
        user.setCreatedAt(new Date());
        user.setId(null);

        if(getUserByEmail(user.getEmail()) != null)
            throw new EmailAlreadyExistsException("User already exists with this email");

        if(user.getPassword().length() < 6)
            throw new InvalidPasswordException("Password must be at least 6 characters");

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User fromDTO(UserDTO userDto) {
        return new User(null, passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(), userDto.getName());
    }
}