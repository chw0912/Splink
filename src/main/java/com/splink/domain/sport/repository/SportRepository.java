package com.splink.domain.sport.repository;

import com.splink.domain.sport.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {

    boolean existsByName(String name);
}
