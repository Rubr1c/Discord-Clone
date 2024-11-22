package dev.rubric.discord.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public void createUserSession(Long userId, HttpSession session) {
        session.setAttribute("USER_ID", userId);
    }

    public Long getUserFromSession(HttpSession session) {
        return (Long) session.getAttribute("USER_ID");
    }

    public Long deleteSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        session.invalidate();
        return userId;
    }
}
