package com.splink.domain.sport.controller;

import com.splink.domain.sport.dto.SportCreateRequest;
import com.splink.domain.sport.dto.SportResponse;
import com.splink.domain.sport.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SportResponse create(@RequestBody SportCreateRequest request) {
        return sportService.create(request);
    }

    @GetMapping
    public List<SportResponse> findAll() {
        return sportService.findAll();
    }

    @GetMapping("/{id}")
    public SportResponse findById(@PathVariable Long id) {
        return sportService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sportService.delete(id);
    }
}
