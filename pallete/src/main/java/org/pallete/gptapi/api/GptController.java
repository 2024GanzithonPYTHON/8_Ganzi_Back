package org.pallete.gptapi.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.gptapi.api.dto.GptResDto;
import org.pallete.gptapi.application.GptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class GptController {

    private final GptService gptService;

    @Operation(summary = "인증된 사용자가 gpt에게 격려의 메세지 요청", description = "인증된 사용자가 gpt가 어제의 일기와 오늘의 일기를 비교하여 보내준 격려의 메세지를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/message")
    public ResponseEntity<GptResDto> compareDiaries(HttpServletRequest request) {
        GptResDto comparisonResult = gptService.compareDiary(request);
        return ResponseEntity.ok(comparisonResult);
    }
}
