package org.myungkeun.crud_r2dbc_webflux_2404112.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("publoc.blog")
public class Blog extends Base{

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("category")
    private String category;

}
