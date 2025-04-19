package com.example.CloudBalanceBackend.security.Services;

import com.example.CloudBalanceBackend.model.User;
import com.example.CloudBalanceBackend.repository.UserRepository;
import com.example.CloudBalanceBackend.security.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(""));
        return UserDetailsImpl.build(user);
    }
}
