package io.github.vinjustino.taskmanagement.controller;

import io.github.vinjustino.taskmanagement.dto.request.LoginRequest;
import io.github.vinjustino.taskmanagement.dto.response.LoginResponse;
import io.github.vinjustino.taskmanagement.entity.User;
import io.github.vinjustino.taskmanagement.service.TokenService;
import io.github.vinjustino.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authenticate = authenticationManager.authenticate(userAndPass);
        User user = (User) authenticate.getPrincipal();
        String token = tokenService.generateToken(user);
        return new LoginResponse(token);
    }
}