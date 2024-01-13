package com.example.onlinebookstore.security;

import com.example.onlinebookstore.dto.user.UserLoginRequestDto;
import com.example.onlinebookstore.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        val authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        val token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
