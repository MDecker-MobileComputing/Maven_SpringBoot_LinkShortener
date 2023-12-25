package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.db;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.KafkaShortLink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * Diese Klasse stellt Methoden für Zugriffe auf Datenbanktabelle bereit.
 */
@Repository
public class Datenbank {

    private Logger LOG = LoggerFactory.getLogger(Datenbank.class);

    /**
     * Bean für Zugriffe auf Datenbanktabellen; die Parameter in den Prepared Statements
     * haben Namen, die mit ":" beginnen, z.B. ":feldname". Die Parameterwerte werden dann
     * über eine Instanz der Klasse {@code MapSqlParameterSource} übergeben.
     */
    private NamedParameterJdbcTemplate _namedParameterJdbcTemplate;


    /**
     * Konstruktor für Dependency Injection.
     */
    public Datenbank(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        _namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

        String sql = "MERGE INTO kurzlinks(key(id), url_original, url_kuerzel, beschreibung, zeitpunkt_erzeugung, ist_aktiv) " +
                     "KEY(id) " +
                     "VALUES(:id, :urlOriginal, :urlKuerzel, :beschreibung, :zeitpunktErzeugung, :istAktiv)";

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

}