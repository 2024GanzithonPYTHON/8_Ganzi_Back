package org.pallete.score.domain.repository;


import org.pallete.score.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findById(Score score);

    Optional<List<Score>> findByUserEmail(String email);

    List<Score> findAllByUserId(Long id);

    Optional<Score> findByUserIdAndDiaryId(Long userId, Long diaryId);
}
