package org.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.task.domain.User;
import org.task.repository.UserRepository;
import org.task.security.UserSecurity;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repo.findByEmail(email);

        if(user == null)
            throw new UsernameNotFoundException(email);

        return new UserSecurity(user.getId(), user.getEmail(), user.getPassword(), user.getName(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}