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


    /** Bean für Datenbankzugriffe. */
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
    public int holeAnzahl() {

        try {

            return _jdbcTemplate.queryForObject("SELECT COUNT(*) FROM urls", Integer.class);
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Auslesen der Anzahl der Datensätze in Tabelle URLDEF.", ex);
            return -1;
        }
    }


    /**
     * Eine neue URL-Definition in die Datenbank einfügen.
     * 
     * @param urlOriginal Originale (lange) URL
     * @param urlKuerzel Kürzel für die URL, z.B. "ab2"
     * @param beschreibung Beschreibungstext
     * @param passwort Passwort, das für eine evtl. Änderung angegeben werden muss
     * @param istAutogeneriert {@code true} wenn das Kürzel automatisch generiert wurde, sonst {@code false}
     * 
     * @return {@code true} wenn das Einfügen erfolgreich war, sonst {@code false}
     */
    public boolean neueKurzUrl(String urlOriginal, String urlKuerzel, String beschreibung, String passwort, boolean istAutogeneriert) {

        String sql = "INSERT INTO urls (url_original, url_kuerzel, beschreibung, passwort, ist_autogeneriert) VALUES (?, ?, ?, ?, ?)";
        try {

            _jdbcTemplate.update(sql, urlOriginal, urlKuerzel, beschreibung, passwort, istAutogeneriert);
            return true;
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Einfügen eines neuen Datensatzes in Tabelle URLDEF für die folgende Original-URL: {}", urlOriginal, ex);
            return false;
        }
    }

}
