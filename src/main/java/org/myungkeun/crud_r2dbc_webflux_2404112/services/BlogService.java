package org.myungkeun.crud_r2dbc_webflux_2404112.services;

import org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Blog> createBlog(BlogRequest request);

    Flux<Blog> getAllBlogs();

    Mono<Blog> getBlogById(Long id);
}
