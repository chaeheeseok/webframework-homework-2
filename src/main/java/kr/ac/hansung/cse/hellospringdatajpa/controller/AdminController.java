package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // ROLE_ADMIN만 접근 가능하도록 설정
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String showUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_control";
    }
}