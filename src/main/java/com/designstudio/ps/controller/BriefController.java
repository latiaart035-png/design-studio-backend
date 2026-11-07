package com.designstudio.ps.controller;

import com.designstudio.ps.dto.BriefRequest;
import com.designstudio.ps.dto.BriefResponse;
import com.designstudio.ps.model.User;
import com.designstudio.ps.repository.UserRepository;
import com.designstudio.ps.service.BriefService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/briefs")
@RequiredArgsConstructor
public class BriefController {

    private final BriefService briefService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BriefResponse> createBrief(@Valid @RequestBody BriefRequest request,
                                                     Principal principal) { // Используем Principal
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Находим пользователя в базе по имени, которое пришло из токена
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BriefResponse response = briefService.processBrief(request, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}