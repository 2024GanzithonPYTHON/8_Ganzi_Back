package org.pallete.login.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 회원가입
public record UserSignUpReqDto(
        @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
                message = "이메일 형식에 맞지 않습니다.")
        String email,

        @NotBlank(message = "이름은 필수로 입력해야 합니다.")
        @Size(min = 2, message = "이름은 최소 2자 이상 입력해야 합니다.")
        String name,

        @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String checkPassword
) {
    public boolean passwordsMatch() {
        return password.equals(checkPassword);
    }
}
