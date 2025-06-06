package com.example.stoservice.service;

import com.example.stoservice.dto.request.LoginRequest;
import com.example.stoservice.dto.request.RegisterRequest;
import com.example.stoservice.entity.User;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.exception.AlreadyExistsException;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistsException("User with this email already exists");
        }
        User user = userService.createUser(request, UserRole.CLIENT);
        log.info("User {} has been registered", user.getEmail());
        return user;
    }

    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("User {} logged in", authentication.getName());
        return jwtService.createToken(userDetails);
    }

}
