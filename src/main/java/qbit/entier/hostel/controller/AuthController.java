package qbit.entier.hostel.controller;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.dto.LoginRequest;
import qbit.entier.hostel.dto.LoginResponse;
import qbit.entier.hostel.dto.UserDto;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.service.CustomUserDetailsService;
import qbit.entier.hostel.service.UserService;
import qbit.entier.hostel.util.JwtUtil;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    @Autowired
    private UserService service;
    
    @GetMapping(value = "/info")
    public UserDto getInfo() {
    	return service.getUserInfo();
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid credentials"));
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
