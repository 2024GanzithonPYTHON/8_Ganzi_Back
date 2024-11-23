package org.pallete.diary.repository;

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
    List<Diary> findByUserEmailAndCreatedAt(String email, LocalDate localDate);
    List<Diary> findByUserEmailAndCreatedAtBetween(String email, LocalDateTime startDate, LocalDateTime endDate);

    List<Diary> findByUserEmail(String email);
    @Query("SELECT d FROM Diary d WHERE d.createdAt = :today AND d.isVisible = true ORDER BY function('RAND') ")
    List<Diary> findRandomDiariesTodayAndIsVisibleTrue(@Param("today") LocalDate today, Pageable pageable);

    @Query("SELECT d FROM Diary d JOIN d.likes l WHERE l.user.email = :email")
    List<Diary> findDiariesLikedByUserEmail(String email);

    // 사용자의 일기 중 점수가 가장 높은 일기 최신 1개 조회
    @Query("SELECT d FROM Diary d JOIN d.score s WHERE d.user.email = :email ORDER BY s.score DESC, d.createdAt DESC")
    List<Diary> findTopDiaryByUserEmailOrderByScoreDesc(String email, Pageable pageable);
}