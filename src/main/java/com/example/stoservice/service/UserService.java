package com.example.stoservice.service;

import com.example.stoservice.dto.request.UserCreateRequest;
import com.example.stoservice.dto.request.UserUpdateRequest;
import com.example.stoservice.entity.User;
import com.example.stoservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findUserByEmailOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreateRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        User saved = userRepository.save(user);
        log.info("User created with id: {}", saved);
        return saved;
    }

    @Transactional
    public User updateUser(String email, UserUpdateRequest request) {
        User user = findUserByEmailOrThrow(userRepository, email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        User saved = userRepository.save(user);
        log.info("User updated with id: {}", saved);
        return saved;
    }

    @Transactional(readOnly = true)
    public User getUser(String email) {
        return findUserByEmailOrThrow(userRepository, email);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        log.info("Getting all users");
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = findUserByEmailOrThrow(userRepository, email);
        userRepository.delete(user);
        log.info("User deleted with id: {}", user);
    }
}
