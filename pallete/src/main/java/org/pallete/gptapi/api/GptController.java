package org.pallete.gptapi.api;

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

    @PostMapping("/message")
    public ResponseEntity<GptResDto> compareDiaries(HttpServletRequest request) {
        GptResDto comparisonResult = gptService.compareDiary(request);
        return ResponseEntity.ok(comparisonResult);
    }
}
