package org.pallete.score.domain.repository;


import org.pallete.diary.domain.Diary;
import org.pallete.score.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByUserEmailAndDiaryId(String email, Long diaryId);
    Optional<Score> findByDiaryId(Long diaryId);
    List<Score> findAllByUser_EmailAndCreateDateBetween(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT EXTRACT(YEAR FROM s.createDate) AS year, EXTRACT(MONTH FROM s.createDate) AS month, AVG(s.score) AS averageScore " +
            "FROM Score s WHERE s.user.email = :email AND EXTRACT(YEAR FROM s.createDate) = :year " +
            "GROUP BY EXTRACT(YEAR FROM s.createDate), EXTRACT(MONTH FROM s.createDate) ")
    List<Object[]> findAverageScoreByUser_EmailAndYear(String email, @Param("year") int year);
}
