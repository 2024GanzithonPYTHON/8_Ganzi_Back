package org.pallete.like.domain.repository;

import org.pallete.diary.domain.Diary;
import org.pallete.diary.domain.User;
import org.pallete.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // 사용자가 좋아요 누른 일기 리스트 조회
    // List<Like> findByUserLikeCount(userId);

    Optional<Like> findByDiaryAndUser(Diary diary, User user);

}
