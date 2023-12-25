package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db;

import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein.DatumFunktionen.timestamp2Date;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.UrlDefinition;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * URL-Definition mit {@code kuerzel} ändern.
     *
     * @param kuerzel Kürzel der URL-Definition, die geändert werden soll
     *
     * @param langUrlNeu Lang-URL, die gesetzt werden soll
     *
     * @param beschreibungNeu Beschreibungstext, der gesetzt werden soll
     *
     * @param istAktivNeu Aktiv-Status, der gesetzt werden soll
     *
     * @return {@code true} wenn das Ändern erfolgreich war, sonst {@code false}
     */
    public boolean aendereUrlDefinition(String kuerzel, String langUrlNeu, String beschreibungNeu, boolean istAktivNeu) {

            final String sql =
                "UPDATE urls " +
                "SET url_original = ?, beschreibung = ?, ist_aktiv = ?, zeitpunkt_aenderung = ? " +
                "WHERE url_kuerzel = ?";

            Date datumZeitJetzt = new Date();

            try {

                int anzZeilenGaendert = _jdbcTemplate.update(sql,
                                                             langUrlNeu, beschreibungNeu, istAktivNeu, datumZeitJetzt, kuerzel);

                return anzZeilenGaendert == 1;
            }
            catch (DataAccessException ex) {

                LOG.error("Fehler beim Ändern der Beschreibung der URL-Definition mit Kuerzel \"{}\".", kuerzel, ex);
                return false;
            }
    }


    /**
     * Bestimmt die höchste ID in der Tabelle {@code URLS}. Wird umd 1 erhöht für die
     * Generierung des nächsten Kürzel verwendet.
     *
     * @return Die höchste ID (Primärschlüssel) in der Tabelle {@code URLS} oder -1 bei Fehler.
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


    /**
     * URL-Definition mit {@code kuerzel} holen.
     *
     * @param kuerzel Kürzel für Datensatz, der geholt werden soll
     *
     * @return {@code Optional} mit Objekt, das alle Felder enthält (wenn gefunden),
     *         sonst leeres {@code Optional}
     */
    public Optional<UrlDefinition> holeDatensatz(String kuerzel) {

        String sql = "SELECT id, url_original, url_kuerzel, beschreibung, zeitpunkt_erzeugung, zeitpunkt_aenderung, ist_aktiv, passwort " +
                     "FROM urls " +
                     "WHERE url_kuerzel = ?";

        List<UrlDefinition> ergebnisListe = _jdbcTemplate.query(
            sql,
            (resultRecord, zeilenNummer) -> new UrlDefinition(
                resultRecord.getInt("id"),
                resultRecord.getString("url_original"),
                resultRecord.getString("url_kuerzel"),
                resultRecord.getString("beschreibung"),
                timestamp2Date(resultRecord.getTimestamp("zeitpunkt_erzeugung")),
                timestamp2Date(resultRecord.getTimestamp("zeitpunkt_aenderung")),
                resultRecord.getBoolean("ist_aktiv"),
                resultRecord.getString("passwort")
            ),
            kuerzel
        );

        if (ergebnisListe.size() > 1) {

            LOG.warn("Mehr als ein Datensatz mit Kuerzel \"{}\" gefunden.", kuerzel);
            return Optional.empty();
        }

        if (ergebnisListe.size() == 1) {

            return Optional.of(ergebnisListe.get(0));
        }
        else {

            return Optional.empty();
        }
    }

}
