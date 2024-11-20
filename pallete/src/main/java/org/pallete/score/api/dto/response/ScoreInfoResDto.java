package org.pallete.score.api.dto.response;

import lombok.Builder;
import org.pallete.score.domain.Score;

import java.time.LocalDateTime;

@Builder
public record ScoreInfoResDto(
        Long diaryId,
        int score,
        String review,
        LocalDateTime createDate
) {
    public static ScoreInfoResDto from(Score score) {
        return ScoreInfoResDto.builder()
                .score(score.getScore())
                .review(score.getReview())
                .createDate(score.getCreateDate())
                .build();
    }
}
