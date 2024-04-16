package org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Blog;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BlogResponse {
    private Blog data;
}
