package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein;

import org.apache.commons.validator.routines.UrlValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Die Klasse ist mit {@code Configuration} annotiert, weil sie mit {@code @Bean}
 * annotierte Methoden hat, die Objekte für Dependency Injection bereitstellen.
 */
@Configuration
public class BohnenFabrik {

    /**
     * Erzeugt ein UrlValidator-Objekt.
     *
     * @return UrlValidator, mit dem URLs auf Gültigkeit überprüft werden können.
     */
    @Bean
    public UrlValidator urlValidator() {

        final String[] urlSchemeArray = {"http", "https", "ftp"};

        return new UrlValidator(urlSchemeArray);
    }

}