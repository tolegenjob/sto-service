package com.example.stoservice.service;

import com.example.stoservice.dto.request.RegisterRequest;
import com.example.stoservice.dto.request.UpdateMeRequest;
import com.example.stoservice.dto.request.UserUpdateRequest;
import com.example.stoservice.entity.User;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.EntityUtil.findUserByEmailOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(RegisterRequest request, UserRole role) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber().replaceAll("\\s+", ""));
        user.setRole(role);
        User saved = userRepository.save(user);
        log.info("User created with id: {}", saved);
        return saved;
    }

    @Transactional
    public User updateUser(Long id, UserUpdateRequest request) {
        User user = findOrThrow(userRepository, id, "User");
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber().replaceAll("\\s+", ""));
        user.setRole(request.role());
        User saved = userRepository.save(user);
        log.info("User updated with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public User updateMe(UserDetails userDetails, UpdateMeRequest request) {
        User user = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber().replaceAll("\\s+", ""));
        User saved = userRepository.save(user);
        log.info("User updated itself with id: {}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public User getMe(UserDetails userDetails) {
        return findUserByEmailOrThrow(userRepository, userDetails.getUsername());
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return findOrThrow(userRepository, id, "User");
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        log.info("Getting all users");
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = findOrThrow(userRepository, id, "User");
        userRepository.delete(user);
        log.info("User deleted with id: {}", user);
    }
}
