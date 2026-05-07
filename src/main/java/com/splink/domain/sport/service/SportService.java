package com.splink.domain.sport.service;

import com.splink.domain.sport.dto.SportCreateRequest;
import com.splink.domain.sport.dto.SportResponse;
import com.splink.domain.sport.entity.Sport;
import com.splink.domain.sport.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SportService {

    private final SportRepository sportRepository;

    @Transactional
    public SportResponse create(SportCreateRequest request) {
        if (sportRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("이미 존재하는 종목입니다.");
        }
        Sport sport = Sport.builder()
                .name(request.name())
                .build();
        try {
            return SportResponse.from(sportRepository.save(sport));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 종목입니다.");
        }
    }

    public List<SportResponse> findAll() {
        return sportRepository.findAll().stream()
                .map(SportResponse::from)
                .toList();
    }

    public SportResponse findById(Long id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종목입니다."));
        return SportResponse.from(sport);
    }

    @Transactional
    public void delete(Long id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종목입니다."));
        sportRepository.delete(sport);
    }
}
