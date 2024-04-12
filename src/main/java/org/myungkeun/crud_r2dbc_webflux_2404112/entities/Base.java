package org.myungkeun.crud_r2dbc_webflux_2404112.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class Base {
    @Id
    @Column("id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column("created_by")
    @CreatedBy
    private String createdBy;

    @Column("updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column("is_Deleted")
    private Boolean isDeleted;

    @Version
    private Integer version;
}
