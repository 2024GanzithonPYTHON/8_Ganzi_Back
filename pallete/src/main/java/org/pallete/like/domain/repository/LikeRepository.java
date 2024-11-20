package org.pallete.like.domain.repository;

import org.pallete.diary.domain.Diary;
import org.pallete.like.domain.Like;
import org.pallete.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByDiaryAndUser(Diary diary, User user);

}
