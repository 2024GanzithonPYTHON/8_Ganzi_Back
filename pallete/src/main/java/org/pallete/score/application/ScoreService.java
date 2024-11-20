package org.pallete.score.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.diary.domain.Diary;
import org.pallete.diary.domain.User;
import org.pallete.diary.repository.DiaryRepository;
import org.pallete.diary.repository.UserRepository;
import org.pallete.score.api.dto.request.ScoreSaveReqDto;
import org.pallete.score.api.dto.response.ScoreInfoResDto;
import org.pallete.score.api.dto.response.ScoreListResDto;
import org.pallete.score.domain.Score;
import org.pallete.score.domain.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    // 점수 생성 - 사용자가 해당 다이어리에 점수를 매길 때
    @Transactional
    public ScoreInfoResDto scoreSave(Long diaryId, ScoreSaveReqDto scoreSaveReqDto, Principal principal) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기가 없습니다."));

        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Score score = scoreSaveReqDto.toEntity(user, diary);
        scoreRepository.save(score);

        return ScoreInfoResDto.from(score);
    }

    // 사용자에 따라 점수 전체 조회
    public ScoreListResDto scoreFindAll(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        List<Score> userScores = scoreRepository.findAllByUserId(userId);

        List<ScoreInfoResDto> scoreInfoResDtoList = userScores.stream()
                .map(ScoreInfoResDto::from)
                .toList();

        return ScoreListResDto.from(scoreInfoResDtoList);
    }

    // 일기에 따라 점수 한 개만 조회 - 사용자의 특정 일기에 대한 점수
    public ScoreInfoResDto scoreFindOne(Long diaryId, Principal principal) {
        Long id = Long.parseLong(principal.getName());

        // 사용자의 특정 일기에 대해 하나의 점수를 조회 (userId와 diaryId를 기준으로)
        Score score = scoreRepository.findByUserIdAndDiaryId(id, diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기의 점수를 찾을 수 없습니다."));

        return ScoreInfoResDto.from(score);
    }

}
