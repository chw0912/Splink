package com.splink.domain.user;

import com.splink.domain.user.dto.UserCreateRequest;
import com.splink.domain.user.dto.UserResponse;
import com.splink.domain.user.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void 회원가입_성공() {
        UserCreateRequest request = new UserCreateRequest("test@example.com", "password123", "홍길동");

        UserResponse response = userService.create(request);

        assertThat(response.email()).isEqualTo("test@example.com");
        assertThat(response.name()).isEqualTo("홍길동");
    }

    @Test
    void 이메일_중복_회원가입_실패() {
        UserCreateRequest request = new UserCreateRequest("test@example.com", "password123", "홍길동");
        userService.create(request);

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 이메일입니다.");
    }

    @Test
    void 회원_단건_조회() {
        UserCreateRequest request = new UserCreateRequest("test@example.com", "password123", "홍길동");
        UserResponse created = userService.create(request);

        UserResponse found = userService.findById(created.id());

        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.email()).isEqualTo("test@example.com");
    }

    @Test
    void 존재하지_않는_회원_조회_실패() {
        assertThatThrownBy(() -> userService.findById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    void 회원_정보_수정() {
        UserCreateRequest createRequest = new UserCreateRequest("test@example.com", "password123", "홍길동");
        UserResponse created = userService.create(createRequest);

        UserUpdateRequest updateRequest = new UserUpdateRequest("김철수", null);
        UserResponse updated = userService.update(created.id(), updateRequest);

        assertThat(updated.name()).isEqualTo("김철수");
    }

    @Test
    void 회원_탈퇴_후_조회_실패() {
        UserCreateRequest request = new UserCreateRequest("test@example.com", "password123", "홍길동");
        UserResponse created = userService.create(request);

        userService.delete(created.id());

        assertThatThrownBy(() -> userService.findById(created.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }
}