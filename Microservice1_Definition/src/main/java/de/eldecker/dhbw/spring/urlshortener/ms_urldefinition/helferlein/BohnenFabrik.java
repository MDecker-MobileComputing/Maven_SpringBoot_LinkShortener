package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * Die Klasse ist mit {@code Configuration} annotiert, weil sie mit {@code @Bean}
 * annotierte Methoden hat, die Objekte für Dependency Injection bereitstellen.
 */
@Configuration
@EnableTransactionManagement
public class BohnenFabrik {

    private static Logger LOG = LoggerFactory.getLogger(BohnenFabrik.class);

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

    /**
     * Konfiguration Transaktionsmanager für Annotation {@code @Transactional}.
     * Wegen dieser Methode ist die Klasse mit {@code @EnableTransactionManagement} annotiert.
     *
     * @param dataSource Datenquelle, die für die Transaktionen verwendet werden soll.
     *                   Die Datenquelle ist entweder HikariCP oder P6Spy
     *                   (für letzteres muss die entsprechende Abhängigkeit in der Datei
     *                    {@code pom.xml}  eingefügt werden)
     * @return Transaktionsmanager für die Datenquelle.
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {

        LOG.info("Transkationsmanager für Datenquelle {} wird erzeugt.",
                 dataSource.getClass().getName());

        return new DataSourceTransactionManager(dataSource);
    }

}