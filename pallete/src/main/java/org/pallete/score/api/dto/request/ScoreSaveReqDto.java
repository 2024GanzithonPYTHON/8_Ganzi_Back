package org.pallete.score.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.pallete.diary.domain.Diary;
import org.pallete.login.model.User;
import org.pallete.score.domain.Score;

public record ScoreSaveReqDto(
        @NotNull(message = "점수는 필수로 입력해야 합니다.")
        @Min(value = 0, message = "점수는 0 이상이어야 합니다.")
        @Max(value = 5, message = "점수는 5 이하이어야 합니다.")
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
