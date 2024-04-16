package org.myungkeun.crud_r2dbc_webflux_2404112.dto.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class RegisterResponse {
    private User user;
}
