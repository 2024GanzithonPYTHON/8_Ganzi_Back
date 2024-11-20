package org.pallete.diary.diary.repository;

import org.pallete.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByCreatedAtAndIsVisibleTrue(LocalDate createdAt, Pageable pageable);
    Optional<Diary> findByUserIdAndCreatedAt(Long userId, LocalDate localDate);
    List<Diary> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);


    @Query("SELECT d FROM Diary d WHERE d.createdAt = :today AND d.isVisible = true ORDER BY function('RAND') ")
    List<Diary> findRandomDiariesTodayAndIsVisibleTrue(@Param("today") LocalDate today, Pageable pageable);

}