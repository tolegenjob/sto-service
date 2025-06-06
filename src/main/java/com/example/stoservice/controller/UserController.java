package com.example.stoservice.controller;

import com.example.stoservice.dto.request.RegisterRequest;
import com.example.stoservice.dto.request.UpdateMeRequest;
import com.example.stoservice.dto.request.UserUpdateRequest;
import com.example.stoservice.dto.response.UserCreateResponse;
import com.example.stoservice.dto.response.UserResponse;
import com.example.stoservice.dto.response.UserUpdateResponse;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.security.AccessService;
import com.example.stoservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccessService accessService;

    @PostMapping("/{role}")
    public ResponseEntity<UserCreateResponse> createUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid RegisterRequest registerRequest,
            @PathVariable UserRole role
    ) {
        if (!accessService.canCreateUser(userDetails, role)) {
            return ResponseEntity.status(403).build();
        }
        UserCreateResponse response = UserCreateResponse.toDto(userService.createUser(registerRequest, role));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody @Valid UserUpdateRequest updateRequest,
            @PathVariable Long id
    ) {
        UserUpdateResponse response = UserUpdateResponse.toDto(userService.updateUser(id, updateRequest));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UpdateMeRequest updateRequest
    ) {
        UserUpdateResponse response = UserUpdateResponse.toDto(userService.updateMe(userDetails, updateRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserResponse> responses = userService.getAllUsers(pageable)
                .map(UserResponse::toDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = UserResponse.toDto(userService.getUserById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse response = UserResponse.toDto(userService.getMe(userDetails));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
