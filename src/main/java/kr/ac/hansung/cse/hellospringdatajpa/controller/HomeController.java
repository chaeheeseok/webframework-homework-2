package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 1. 홈 - 로그인 여부에 따라 분기
    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        return "redirect:/products";
    }

    // 2. 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // 3. 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return "redirect:/login";
    }

    // 4. 로그인 폼
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}