package org.myungkeun.crud_r2dbc_webflux_2404112.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog.BlogResponse;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Base;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Blog;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor

public class BlogController {
    private final BlogService blogService;

    @PostMapping
    Mono<ResponseEntity<BaseResponse<BlogResponse>>> createBlog(
            @RequestBody BlogRequest request
    ) {
        return blogService.createBlog(request)
                .map(saveBlog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("success")
                        .data(new BlogResponse(saveBlog))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }

    @GetMapping
    Mono<ResponseEntity<BaseResponse<BlogResponse>>> getBlogById(
            @PathVariable(name = "id") Long id
    ) {
        return blogService.getBlogById(id)
                .map(blog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new BlogResponse(blog))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }

    @GetMapping
    Flux<ResponseEntity<BaseResponse<BlogResponse>>> getAllBlog() {
        return blogService.getAllBlogs()
                .map(blog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new BlogResponse(blog))
                        .build()))
                .onErrorResume(throwable -> Flux.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }
}
