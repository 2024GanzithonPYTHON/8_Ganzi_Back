package org.pallete.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // bean 관리
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() { // swagger-ui에 표시될 api 문서 구성 객체
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo()); // 바로 뒤에 만들 예정이라 빨간줄이어도 상관 없음
    }
    // info 임포트는 oas.models.info.Info로
    private Info apiInfo() {
        return new Info()
                .title("Record With Swagger") // api의 제목
                .description("Record With 스웨거입니다.") // api 설명
                .version("1.0.0"); // api 버전
    }
}