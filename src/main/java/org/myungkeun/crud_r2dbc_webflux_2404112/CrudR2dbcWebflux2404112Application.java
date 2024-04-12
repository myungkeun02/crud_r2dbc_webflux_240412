package org.myungkeun.crud_r2dbc_webflux_2404112;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableR2dbcRepositories

public class CrudR2dbcWebflux2404112Application {

    public static void main(String[] args) {
        SpringApplication.run(CrudR2dbcWebflux2404112Application.class, args);
    }

}
