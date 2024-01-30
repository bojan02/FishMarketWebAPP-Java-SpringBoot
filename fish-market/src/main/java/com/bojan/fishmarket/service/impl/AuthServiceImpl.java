package com.bojan.fishmarket.service.impl;

import com.bojan.fishmarket.dto.LoginDTO;
import com.bojan.fishmarket.dto.RegisterDTO;
import com.bojan.fishmarket.entity.Role;
import com.bojan.fishmarket.entity.User;
import com.bojan.fishmarket.exception.FishMarketsAPIException;
import com.bojan.fishmarket.repository.RoleRepository;
import com.bojan.fishmarket.repository.UserRepository;
import com.bojan.fishmarket.security.JwtTokenProvider;
import com.bojan.fishmarket.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDTO registerDTO) {

        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new FishMarketsAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new FishMarketsAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
