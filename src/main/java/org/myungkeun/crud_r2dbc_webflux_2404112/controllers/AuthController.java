package org.myungkeun.crud_r2dbc_webflux_2404112.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.login.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.login.LoginResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public Mono<ResponseEntity<BaseResponse<RegisterResponse>>> register(@RequestBody RegisterRequest request) {
        return authService.register(request)
                .map(user -> ResponseEntity.ok(BaseResponse.<RegisterResponse>builder()
                        .statusCode(201)
                        .message("success")
                        .data(new RegisterResponse(user))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<RegisterResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new RegisterResponse(null))
                                .build()
                        )));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<BaseResponse<LoginResponse>>> login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request)
                .map(token -> ResponseEntity.ok(BaseResponse.<LoginResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new LoginResponse(token))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(BaseResponse.<LoginResponse>builder()
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message(throwable.getMessage())
                                .data(new LoginResponse(null))
                                .build()
                        )));
    }
}
