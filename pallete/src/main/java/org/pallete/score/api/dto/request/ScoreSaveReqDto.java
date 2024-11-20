package org.pallete.score.api.dto.request;

import org.pallete.diary.domain.Diary;
import org.pallete.diary.domain.User;
import org.pallete.score.domain.Score;

public record ScoreSaveReqDto(
        int score,
        String review
) {
    public Score toEntity(User user, Diary diary) {
        return Score.builder()
                .score(score)
                .review(review)
                .user(user)
                .diary(diary)
                .build();
    }
}
