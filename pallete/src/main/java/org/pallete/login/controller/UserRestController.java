package org.pallete.login.controller;

import org.pallete.login.model.User;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userName", user.getName());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

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