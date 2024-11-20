package org.pallete.score.api.dto.response;

import lombok.Builder;
import org.pallete.score.domain.Score;

import java.time.LocalDateTime;

@Builder
public record ScoreInfoResDto(
        Long id,
        int score,
        String review,
        LocalDateTime createDate
) {
    public static ScoreInfoResDto from(Score score) {
        return ScoreInfoResDto.builder()
                .id(score.getId())
                .score(score.getScore())
                .review(score.getReview())
                .createDate(score.getCreateDate())
                .build();
    }
}
