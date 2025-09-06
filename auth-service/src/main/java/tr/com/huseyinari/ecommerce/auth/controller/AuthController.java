package tr.com.huseyinari.ecommerce.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(
        summary = "Giriş Yap",
        description = "Kullanıcı girişini doğrular ve servislere erişim için geçerli anahtarları verir."
//        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Giriş işlemi başarılı",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = LoginResponse.class))
            )
        ),
        @ApiResponse(responseCode = "401", description = "Kullanıcı adı veya şifre hatalı !", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = this.service.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Token Yenile",
        description = "Süresi dolan access token'i yenilemek için kullanılır."
//        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Token yenilendi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = LoginResponse.class))
            )
        ),
        @ApiResponse(responseCode = "401", description = "Refresh token hatalı !", content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        LoginResponse response = this.service.refresh(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Kayıt ol",
        description = "Yeni kullanıcı kaydı oluşturur."
//        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Kullanıcı kaydı başarıyla yapıldı.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = RegisterResponse.class))
            )
        ),
        @ApiResponse(responseCode = "500", description = "Kayıt işlemi başarısız !", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse response = this.service.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
