package org.myungkeun.crud_r2dbc_webflux_2404112.repositories;

import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Blog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BlogRepository extends ReactiveCrudRepository<Blog, Long> {
    Mono<Blog> findBlogByTitle(String title);
}
