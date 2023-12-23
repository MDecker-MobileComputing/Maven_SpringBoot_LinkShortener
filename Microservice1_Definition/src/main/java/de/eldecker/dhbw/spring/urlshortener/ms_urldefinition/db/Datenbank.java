package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Bean-Klasse, die die Datenbankfunktionialität bereitstellt.
 */
@Component
public class Datenbank {
    
    private static Logger LOG = LoggerFactory.getLogger( Datenbank.class );

    private final JdbcTemplate _jdbcTemplate;

    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public Datenbank(JdbcTemplate jdbcTemplate) {

        _jdbcTemplate = jdbcTemplate;
    }


    /**
     * Liefert die Anzahl der in der Datenbank gespeicherten URL-Definitionen.
     * 
     * @return Anzahl der in der Datenbank gespeicherten URL-Definitionen oder -1 bei Fehler.
     */
    public int getAnzahl() {

        try {

            return _jdbcTemplate.queryForObject("SELECT COUNT(*) FROM urls", Integer.class);
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Auslesen der Anzahl der Datensätze in Tabelle URLDEF.", ex);
            return -1;
        }
    }

}
