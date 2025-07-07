package org.task.service;

import org.task.domain.User;
import org.task.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.task.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User find(Integer id){

        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @Transactional
    public User insert(User user) {
        user.setCreatedAt(new Date());
        user.setId(null);
        user = userRepository.save(user);
        return user;
    }

    public User fromDTO(UserDTO userDto) {
        User user = new User(null, passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(), userDto.getName());
        return user;
    }
}