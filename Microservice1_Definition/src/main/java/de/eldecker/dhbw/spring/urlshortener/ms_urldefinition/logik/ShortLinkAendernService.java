package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.logik;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.kafka.KafkaSender;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.KafkaUrlDefinition;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.ShortLinkException;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.UrlDefinition;


/**
 * Diese Bean enthält die Business-Logik für das Ändern einer neuen URL-Definition.
 */
@Service
public class ShortLinkAendernService {

    /** Bean für Zugriff auf Datenbank */
    private Datenbank _datenbank;

    /** Bean für Validierung der Lang/Original-URL. */
    private UrlValidator _urlValidator;

    /** Bean für Versenden von Nachricht an andere Microservices via Kafka. */
    private KafkaSender _kafkaSender;


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public ShortLinkAendernService( Datenbank db,
                                    UrlValidator urlValidator,
                                    KafkaSender kafkaSender ) {
        _datenbank    = db;
        _urlValidator = urlValidator;
        _kafkaSender  = kafkaSender;
    }


    /**
     * Änderung einer URL-Defintion, mindestens eines der folgenden Attribute
     * soll geändert werden: Lang-URL, Beschreibung, Aktiv-Status.
     *
     * @param kuerzel Kürzel der URL-Definition, die geändert werden soll
     *
     * @param passwort Passwort für Berechtigungsprüfung
     *
     * @param urlLang Evtl. neue Lang-URL
     *
     * @param beschreibung Evtl. neuer Beschreibungstext
     *
     * @param istAktiv Evtl. neuer Aktiv-Status
     *
     * @throws ShortLinkException Fehler bei Änderung wegen ungültiger Daten oder internem Fehler
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void shortLinkAendern( String kuerzel, String passwort,
                                  String urlLangNeu, String beschreibungNeu, boolean istAktivNeu
                                ) throws ShortLinkException {

        if (_urlValidator.isValid(urlLangNeu) == false) {

            throw new ShortLinkException("Ungültige URL: " + urlLangNeu, false); // false=externerFehler
        }

        Optional<UrlDefinition> ergebnisOptional = _datenbank.holeDatensatz(kuerzel);

        if (ergebnisOptional.isEmpty()) {

            throw new ShortLinkException("Kein Datensatz für Kürzel " + kuerzel + " gefunden", false); // false=externerFehler
        }

        UrlDefinition urlDefinitionAlt = ergebnisOptional.get();
        if (urlDefinitionAlt.passwort().equals(passwort) == false) {

            throw new ShortLinkException("Passwort falsch", false); // false=externerFehler
        }

        // Änderung der URL-Definition über Kafka an andere Microservices verteilen
        Date jetztDate = new Date();

        KafkaUrlDefinition kud = new KafkaUrlDefinition( urlDefinitionAlt.id(),
                                                         urlLangNeu,
                                                         kuerzel,
                                                         beschreibungNeu.trim(),
                                                         urlDefinitionAlt.erzeugtAm(),
                                                         jetztDate,
                                                         istAktivNeu );
        _kafkaSender.sendeUrlDefinition(kud);

        boolean aendernErfolgreich = _datenbank.aendereUrlDefinition(kuerzel, urlLangNeu, beschreibungNeu, istAktivNeu);
        if (aendernErfolgreich == false) {

            throw new ShortLinkException("Fehler beim Ändern der URL-Definition", true); // true=internerFehler
        }
    }

}
