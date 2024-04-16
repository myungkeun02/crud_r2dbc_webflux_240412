package org.myungkeun.crud_r2dbc_webflux_2404112.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.config.filter.ReactiveRequestContextHolder;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.user.UpdateUserProfileResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.user.UpdateUsernameRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.user.UserProfileResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<BaseResponse<UserProfileResponse>>> getUserProfileByToken() {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(userService::getUserProfile)
                .map(email -> ResponseEntity.ok(BaseResponse.<UserProfileResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("get user success")
                        .data(new UserProfileResponse(email))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<UserProfileResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build())));
    }

    @PutMapping
    public Mono<ResponseEntity<BaseResponse<UpdateUserProfileResponse>>> updateUsername(
            @RequestBody UpdateUsernameRequest request
            ) {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(token -> userService.updateUsername(token, request.getNewUsername()))
                .map(user -> ResponseEntity.ok(BaseResponse.<UpdateUserProfileResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new UpdateUserProfileResponse(user.getUsername()))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<UpdateUserProfileResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build())));
    }
}
