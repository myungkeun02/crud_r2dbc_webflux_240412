package org.myungkeun.crud_r2dbc_webflux_2404112.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private List<String> error;
}
