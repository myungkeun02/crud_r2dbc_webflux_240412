package org.myungkeun.crud_r2dbc_webflux_2404112.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterResponseDto;
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
    public Mono<ResponseEntity<BaseResponse<RegisterResponseDto>>> register(@RequestBody RegisterRequestDto request) {
        return authService.register(request)
                .map(user -> ResponseEntity.ok(BaseResponse.<RegisterResponseDto>builder()
                        .statusCode(201)
                        .message("success")
                        .data(new RegisterResponseDto(user))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<RegisterResponseDto>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new RegisterResponseDto(null))
                                .build()
                        )));

    }
}
