package com.splink.domain.sport;

import com.splink.domain.sport.dto.SportCreateRequest;
import com.splink.domain.sport.dto.SportResponse;
import com.splink.domain.sport.repository.SportRepository;
import com.splink.domain.sport.service.SportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SportServiceTest {

    @Autowired
    SportService sportService;

    @Autowired
    SportRepository sportRepository;

    @BeforeEach
    void setUp() {
        sportRepository.deleteAll();
    }

    @Test
    void 종목_등록_성공() {
        SportCreateRequest request = new SportCreateRequest("축구");

        SportResponse response = sportService.create(request);

        assertThat(response.name()).isEqualTo("축구");
    }

    @Test
    void 종목명_중복_등록_실패() {
        sportService.create(new SportCreateRequest("축구"));

        assertThatThrownBy(() -> sportService.create(new SportCreateRequest("축구")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 종목입니다.");
    }

    @Test
    void 전체_종목_조회() {
        sportService.create(new SportCreateRequest("축구"));
        sportService.create(new SportCreateRequest("농구"));

        List<SportResponse> result = sportService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void 종목_단건_조회() {
        SportResponse created = sportService.create(new SportCreateRequest("축구"));

        SportResponse found = sportService.findById(created.id());

        assertThat(found.name()).isEqualTo("축구");
    }

    @Test
    void 존재하지_않는_종목_조회_실패() {
        assertThatThrownBy(() -> sportService.findById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 종목입니다.");
    }

    @Test
    void 종목_삭제_후_조회_실패() {
        SportResponse created = sportService.create(new SportCreateRequest("축구"));

        sportService.delete(created.id());

        assertThatThrownBy(() -> sportService.findById(created.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 종목입니다.");
    }
}
