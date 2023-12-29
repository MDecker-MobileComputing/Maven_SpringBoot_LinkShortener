package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.db;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.AufgeloesterLink;
import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.KafkaShortLink;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * Diese Klasse stellt Methoden für Zugriffe auf Datenbanktabelle bereit.
 */
@Repository
public class Datenbank {

    private Logger LOG = LoggerFactory.getLogger(Datenbank.class);

    /**
     * Bean für Zugriffe auf Datenbanktabellen; die Parameter in den Prepared Statements
     * werden mit "?" gesetzt.
     */
    private JdbcTemplate _jdbcTemplate;

    /**
     * Bean für Zugriffe auf Datenbanktabellen; die Parameter in den Prepared Statements
     * haben Namen, die mit ":" beginnen, z.B. ":feldname". Die Parameterwerte werden dann
     * über eine Instanz der Klasse {@code MapSqlParameterSource} übergeben.
     */
    private NamedParameterJdbcTemplate _namedParameterJdbcTemplate;


    /**
     * Konstruktor für Dependency Injection.
     */
    public Datenbank(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                     JdbcTemplate               jdbcTemplate) {

        _namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        _jdbcTemplate               = jdbcTemplate;
    }


    /**
     * Fügt neuen Datensatz in Tabelle {@code kurzlinks} ein oder aktualisiert einen bestehenden.
     * Wenn ein Datensatz mit der ID {@code id} bereits existiert, wird er aktualisiert, sonst
     * wird er neu eingefügt.
     *
     * @param kafkaShortLink Datensatz, der eingefügt oder aktualisiert werden soll.
     *
     * @return {@code true}, wenn Datensatz eingefügt oder aktualisiert wurde, {@code false}
     *         wenn dabei ein Fehler aufgetreten ist.
     */
    public boolean shortLinkEinfuegenOderAktualisieren(KafkaShortLink kafkaShortLink) {

        String sql = "MERGE INTO kurzlinks(id, url_original, url_kuerzel, beschreibung, zeitpunkt_erzeugung, zeitpunkt_aktualisierung, ist_aktiv) " +
                     "KEY(id) " +
                     "VALUES(:id, :urlOriginal, :urlKuerzel, :beschreibung, :zeitpunktErzeugung, :zeitpunktAktualisierung, :istAktiv)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", kafkaShortLink.id());
        parameters.addValue("urlOriginal", kafkaShortLink.urlOriginal());
        parameters.addValue("urlKuerzel", kafkaShortLink.kuerzel());
        parameters.addValue("beschreibung", kafkaShortLink.beschreibung());
        parameters.addValue("zeitpunktErzeugung", kafkaShortLink.zeitpunktErzeugt());
        parameters.addValue("zeitpunktAktualisierung", kafkaShortLink.zeitpunktGeaendert());
        parameters.addValue("istAktiv", kafkaShortLink.istAktiv());


        try {

            _namedParameterJdbcTemplate.update(sql, parameters);

            return true;
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Einfügen/Aktualisieren eines Datensatzes in Tabelle \"kurzlinks\": {}",
                      ex.getMessage());

            return false;
        }
    }


    /**
     * Liefert die Original-URL (und einige anderen Daten) zu einem URL-Kürzel zurück.
     *
     * @param kuerzel URL-Kürzel, zu dem die Original-URL gesucht wird.
     *
     * @return Leeres Optional, wenn für {@code kuerzel} kein Datensatz gefunden wurde,
     *         sonst ein Optional mit einem Objekt vom Typ {@code AufgeloesterLink},
     *         das die für die Anzeige des aufgelösten Links benötigten Daten enthält.
     */
    public Optional<AufgeloesterLink> kuerzelAufloesen(String kuerzel) {

        // Damit der DataClassRowMapper die Spaltennamen in der Datenbanktabelle abbilden kann,
        // werden mit "AS" die Spaltennamen in der SQL-Abfrage umbenannt;
        // siehe auch Seite 453 in Buch von Ullenboom ( https://amzn.to/48qYPA8 )
        String sql = "SELECT url_original AS urlOriginal, "                         +
                            "beschreibung, "                                        +
                            "zeitpunkt_erzeugung AS zeitpunktErzeugung, "           +
                            "zeitpunkt_aktualisierung AS zeitpunktAktualisierung, " +
                            "ist_aktiv AS istAktiv "                                +
                     "  FROM kurzlinks "                                            +
                     "  WHERE url_kuerzel = ?";

        DataClassRowMapper<AufgeloesterLink> rowMapper = new DataClassRowMapper<>(AufgeloesterLink.class);
        try {

            List<AufgeloesterLink> ergebnisListe = _jdbcTemplate.query(sql, rowMapper, kuerzel);
            if (ergebnisListe.isEmpty()) {

                return Optional.empty();

            } else {

                return Optional.of(ergebnisListe.get(0));
            }
        }
        catch (DataAccessException ex) {

            LOG.error("Fehler beim Auflösen des Kuerzels \"{}\": {}", kuerzel, ex.getMessage());
            return Optional.empty();
        }
    }

}