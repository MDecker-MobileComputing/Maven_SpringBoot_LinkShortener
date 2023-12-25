package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Bean-Klasse, die die Datenbankfunktionialität bereitstellt.
 * <br><br>
 *
 * Die Klasse ist mit {@code @Repository} annotiert, weil sie Datenbankzugriffsfunktionen
 * bereitstellt; diese Annotation ist eine rein semantische Spezialisierung von {@code @Component}.
 */
@Repository
public class Datenbank {

    private static Logger LOG = LoggerFactory.getLogger( Datenbank.class );


    /**
     * Bean für Datenbankzugriffe: {@code JdbcTemplate} von Spring bietet
     * eine höhere Abstraktion als JDBC und vereinfacht die Verwendung von JDBC.
     */
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
     *
     * @param urlKuerzel Kürzel für die URL, z.B. "ab2"
     *
     * @param beschreibung Beschreibungstext
     *
     * @param passwort Passwort, das für eine evtl. Änderung angegeben werden muss
     *
     * @param datumZeitJetzt Aktuelles Datum und Uhrzeit für Felder {@code zeitpunkt_erzeugung} und {@code zeitpunkt_aenderung}
     *
     * @return {@code true} wenn das Einfügen erfolgreich war, sonst {@code false}
     */
    public boolean neueKurzUrl(String urlOriginal, String urlKuerzel, String beschreibung, String passwort, Date datumZeitJetzt) {

        final String sql = "INSERT INTO urls " +
                           "(url_original, url_kuerzel, beschreibung, passwort, zeitpunkt_erzeugung, zeitpunkt_aenderung) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            _jdbcTemplate.update(sql, urlOriginal, urlKuerzel, beschreibung, passwort, datumZeitJetzt, datumZeitJetzt);
            return true;
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Einfügen eines neuen Datensatzes in Tabelle URLDEF für die folgende Original-URL: {}",
                      urlOriginal, ex);
            return false;
        }
    }

    /**
     * Beschreibungstext für eine URL ändern.
     *
     * @param beschreibungNeu Neuer Beschreibungstext
     *
     * @param kuerzel Kürzel der URL-Definition, für die die Beschreibung zu ändern ist
     *
     * @param passwort Passwort für Authentifizierung
     *
     * @return {@code true} wenn das Ändern erfolgreich war (also genau eine Zeile geändert wurde), sonst {@code false}
     */
    public boolean setzeBeschreibung(String beschreibungNeu, String kuerzel, String passwort) {

            final String sql = "UPDATE urls SET beschreibung = ?, zeitpunkt_aenderung = ? WHERE url_kuerzel = ? AND passwort = ?";

            try {

                int anzZeilenGaendert = _jdbcTemplate.update(sql, beschreibungNeu, new Date(), kuerzel, passwort);

                return anzZeilenGaendert == 1;
            }
            catch (DataAccessException ex) {

                LOG.error("Fehler beim Ändern der Beschreibung der URL-Definition mit Kuerzel \"{}\".", kuerzel, ex);
                return false;
            }
    }


    /**
     * Bestimmt die höchste ID in der Tabelle {@code urls}. Wird umd 1 erhöht für die
     * Generierung des nächsten Kürzel verwendet.
     *
     * @return Die höchste ID (Primärschlüssel) in der Tabelle {@code urls} oder -1 bei Fehler.
     */
    public int holeMaxId() {

        try {

            return _jdbcTemplate.queryForObject("SELECT MAX(id) FROM urls", Integer.class);
        }
        catch (DataAccessException ex) {

            LOG.error("Datenbankfehler bei MAX(id).", ex);
            return -1;
        }
    }

}
