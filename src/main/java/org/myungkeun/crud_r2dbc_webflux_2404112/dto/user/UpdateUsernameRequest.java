package org.myungkeun.crud_r2dbc_webflux_2404112.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class UpdateUsernameRequest {
    private String newUsername;
}
