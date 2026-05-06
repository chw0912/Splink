package com.splink.domain.user.service;

import com.splink.domain.user.dto.UserCreateRequest;
import com.splink.domain.user.dto.UserResponse;
import com.splink.domain.user.dto.UserUpdateRequest;
import com.splink.domain.user.entity.User;
import com.splink.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .build();
        try {
            return UserResponse.from(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.update(request.name(), request.password());
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.delete();
    }
}
