package org.pallete.score.domain.repository;


import org.pallete.diary.domain.Diary;
import org.pallete.score.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findById(Score score);

    List<Score> findAllByUserEmail(String email);

    List<Score> findAllByUserId(Long id);

    Optional<Score> findByUserEmailAndDiaryId(String email, Long diaryId);
    Score findByDiary(Diary diary);

}
