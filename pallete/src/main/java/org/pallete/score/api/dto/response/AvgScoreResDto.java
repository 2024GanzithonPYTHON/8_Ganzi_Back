package org.pallete.score.api.dto.response;

import org.pallete.score.domain.Score;

public record AvgScoreResDto(
        int month,
        double avgScore
) {
}
