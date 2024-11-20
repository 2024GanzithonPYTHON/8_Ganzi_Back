package org.pallete.diary.repository;

import org.pallete.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByCreatedAt(LocalDate createdAt, Pageable pageable);
    Optional<Diary> findByUserIdAndCreatedAt(Long userId, LocalDate localDate);
    List<Diary> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}