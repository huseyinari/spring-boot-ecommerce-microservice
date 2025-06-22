package tr.com.huseyinari.ecommerce.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinari.ecommerce.auth.request.LoginRequest;
import tr.com.huseyinari.ecommerce.auth.request.RefreshTokenRequest;
import tr.com.huseyinari.ecommerce.auth.request.RegisterRequest;
import tr.com.huseyinari.ecommerce.auth.response.LoginResponse;
import tr.com.huseyinari.ecommerce.auth.response.RegisterResponse;
import tr.com.huseyinari.ecommerce.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = this.service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        LoginResponse response = this.service.refresh(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse response = this.service.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
