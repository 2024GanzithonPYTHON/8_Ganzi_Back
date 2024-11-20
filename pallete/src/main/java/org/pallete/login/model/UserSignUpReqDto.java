package org.pallete.login.model;

public record UserSignUpReqDto(
        String email,
        String name,
        String password
) {

}
