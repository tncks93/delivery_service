package com.delivery_service.owners.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners/test-session")
public class TestOwnerSessionController {

    @PostMapping()
    public String setOwnerIdSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int ownerId = 1;
        session.setAttribute("ownerId",ownerId);
        String sessId = session.getId();
        return sessId;
    }
}
