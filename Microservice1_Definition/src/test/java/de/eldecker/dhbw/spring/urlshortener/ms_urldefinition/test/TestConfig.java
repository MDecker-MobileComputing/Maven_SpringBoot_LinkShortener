package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;


@Configuration
public class TestConfig {

    /**
     * Datenkbank für Unit-Test konfigurieren.
     *
     * @return Datenbank-Konfiguration für die Unit-Tests.
     */
    @Bean
    public DataSource dataSource() {

        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            //.addScript("classpath:data.sql")
            .build();
    }
}