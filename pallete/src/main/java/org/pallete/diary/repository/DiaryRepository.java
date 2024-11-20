package org.pallete.repository;

import org.pallete.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByCreatedAt(LocalDate createdAt, Pageable pageable);
    Optional<Diary> findByDate(Long userId, LocalDate localDate);
}