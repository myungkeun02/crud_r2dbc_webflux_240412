package org.myungkeun.crud_r2dbc_webflux_2404112.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BlogRequest {
    private String title;
    private String description;
    private String category;
}
