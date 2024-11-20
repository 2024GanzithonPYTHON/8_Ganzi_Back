package org.pallete.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.pallete.login.model.User;
import org.pallete.login.model.UserSignUpReqDto;
import org.pallete.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserSignUpReqDto userSignUpReqDto) {
        if (userService.findByEmail(userSignUpReqDto.email()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = new User();
        user.setEmail(userSignUpReqDto.email());
        user.setName(userSignUpReqDto.name());
        user.setPassword(userSignUpReqDto.password());

        userService.saveUser(user);
        return ResponseEntity.ok("Registration successful");
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userEmail", user.getEmail());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/session")
    public ResponseEntity<String> getSession(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            return ResponseEntity.ok("Logged in as " + userName);
        } else {
            return ResponseEntity.status(401).body("Not logged in");
        }
    }
}