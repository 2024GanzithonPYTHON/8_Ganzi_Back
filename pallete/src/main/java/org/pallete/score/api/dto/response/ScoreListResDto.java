package org.pallete.score.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ScoreListResDto(
        List<ScoreInfoResDto> scores
) {
    public static ScoreListResDto from(List<ScoreInfoResDto> scores) {
        return ScoreListResDto.builder()
                .scores(scores)
                .build();

    }
}
