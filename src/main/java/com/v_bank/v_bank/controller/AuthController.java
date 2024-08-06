package com.v_bank.v_bank.controller;

import com.v_bank.v_bank.dto.UserDto;
import com.v_bank.v_bank.service.UserService;
import com.v_bank.v_bank.utils.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDto.UserLoginInfo userLoginInfo, HttpServletResponse response) {
        UserDto user = userService.login(userLoginInfo);

        String token = jwtProvider.generateToken(user.getEmail());
        Cookie cookie = new Cookie("AccessToken", token);
        cookie.setMaxAge(60*60*24);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserDto.UserRegisterInfo userRegisterInfo) {
        userService.register(userRegisterInfo);
    }

}
