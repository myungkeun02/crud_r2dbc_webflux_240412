package org.myungkeun.crud_r2dbc_webflux_2404112.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginResponse {
    private String accessToken;
}
