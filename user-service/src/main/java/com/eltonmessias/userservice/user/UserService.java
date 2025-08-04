package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.exception.UserNotFoundException;
import com.eltonmessias.userservice.tenant.TenantRepository;
import com.eltonmessias.userservice.tenant.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public UserResponse createUser(@Valid UserRequest userRequest) {
        var user  = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUSerById(UUID userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));
        return userMapper.toUserResponse(user);
    }
}
