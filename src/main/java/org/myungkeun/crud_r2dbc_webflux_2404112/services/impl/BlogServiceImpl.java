package org.myungkeun.crud_r2dbc_webflux_2404112.services.impl;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Blog;
import org.myungkeun.crud_r2dbc_webflux_2404112.repositories.BlogRepository;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.BlogService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor

public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Override
    public Mono<Blog> createBlog(BlogRequest request) {
        Blog newBlog = new Blog(request.getTitle(), request.getDescription(), request.getCategory());
        return blogRepository.save(newBlog);
    }

    @Override
    public Flux<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Mono<Blog> getBlogById(Long id) {
        return blogRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("blog not found")));
    }
}
