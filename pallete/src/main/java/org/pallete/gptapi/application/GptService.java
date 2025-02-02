package org.pallete.gptapi.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.diary.domain.Diary;
import org.pallete.diary.repository.DiaryRepository;
import org.pallete.gptapi.api.dto.GptReqDto;
import org.pallete.gptapi.api.dto.GptResDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GptService {

    private final RestTemplate restTemplate;
    private final DiaryRepository diaryRepository;
    private final String model;
    private final ObjectMapper objectMapper;

    public GptResDto compareDiary(HttpServletRequest request) {
        String userEmail = getEmailFromSession(request);

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // 오늘과 어제의 일기 조회
        List<Diary> todayDiaries = diaryRepository.findByUserEmailAndCreatedAt(userEmail, today);
        List<Diary> yesterdayDiaries = diaryRepository.findByUserEmailAndCreatedAt(userEmail, yesterday);

        if (todayDiaries.isEmpty() || yesterdayDiaries.isEmpty()) {
            throw new IllegalArgumentException("어제 또는 오늘의 일기가 없습니다.");
        }

        Diary todayDiary = todayDiaries.get(0);
        Diary yesterdayDiary = yesterdayDiaries.get(0);

        String prompt = String.format(
                "어제 일기: %s\n오늘 일기: %s\n" + "두 일기를 비교해서 간단한 격려의 메세지 적어주세요. 코멘트 형식으로"
                , yesterdayDiary.getContent(), todayDiary.getContent()
        );

        // GPT API 요청 생성
        return callOpenAiApi(prompt, model);

    }

    private GptResDto callOpenAiApi(String prompt, String model) {
        GptReqDto gptReqDto = new GptReqDto(model, prompt);

        // RestTemplate으로 API 호출
        HttpEntity<GptReqDto> entity = new HttpEntity<>(gptReqDto);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            return objectMapper.readValue(responseEntity.getBody(), GptResDto.class);
        } catch (Exception e) {
            log.error("Failed to parse GPT response", e);
            throw new RuntimeException("GPT 응답을 처리하는 중 오류가 발생했습니다.", e);
        }
    }

    // 세션에서 이메일 가져오기
    private String getEmailFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
        return (String) session.getAttribute("userEmail");
    }
}
