package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.logik;

import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein.StringFunktionen.erzeugePasswort;
import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein.StringFunktionen.zahlZuString;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.kafka.KafkaSender;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.KafkaUrlDefinition;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.KuerzelUndPasswort;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.ShortLinkException;

import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Diese Bean enthält die Business-Logik für das Anlegen einer neuen URL-Definition.
 */
@Service
public class ShortLinkErzeugenService {

        /** Bean für Zugriff auf Datenbank */
    private Datenbank _datenbank;

    /** Bean für Validierung der Lang/Original-URL. */
    private UrlValidator _urlValidator;

    /** Bean für Versenden von Nachricht an andere Microservices via Kafka. */
    private KafkaSender _kafkaSender;


    /**
     * Konstruktor für Dependency Injection.
     */
    public ShortLinkErzeugenService( Datenbank db,
                                     UrlValidator urlValidator,
                                     KafkaSender kafkaSender ) {
        _datenbank    = db;
        _urlValidator = urlValidator;
        _kafkaSender  = kafkaSender;
    }

    /**
     * Neue ShortLink-Definition auf DB anlegen.
     *
     * @param urlLang Vom Nutzer eingegebene (Lang-)URL
     * @param beschreibung Optionale Beschreibung der URL
     * @return Objekt mit Kürzel und Passwort für die angelegte URL-Definition
     * @throws ShortLinkException Wenn die URL ungültig ist oder ein DB-Fehler aufgetreten ist.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public KuerzelUndPasswort shortlinkAnlegen(String urlLang,
                                               String beschreibung) throws ShortLinkException {

        final String urlLangTrimmed      = urlLang.trim();
        final String beschreibungTrimmed = beschreibung.trim();

        if (urlLangTrimmed.isBlank()) {
            throw new ShortLinkException("Leere URL von Nutzer erhalten.", false);
        }
        if (urlLangTrimmed.length() > 999) {

            throw new ShortLinkException("Zu lange URL von Nutzer erhalten: " + urlLangTrimmed, false);
        }
        if (_urlValidator.isValid(urlLangTrimmed) == false) {

            throw new ShortLinkException("Ungültige URL von Nutzer erhalten: " + urlLangTrimmed, false);
        }

        final int maxId = _datenbank.holeMaxId();
        if (maxId == -1) {

            throw new ShortLinkException("Datenbankfehler beim Ermitteln der maximalen ID.", true);
        }

        final int    idNeu          = maxId + 1;
        final String kuerzel        = zahlZuString(idNeu);
        final String passwort       = erzeugePasswort();
        final Date   datumZeitJetzt = new Date();

        // URL-Definition über Kafka an andere Microservices verteilen
        KafkaUrlDefinition kud = new KafkaUrlDefinition( idNeu,
                                                         urlLangTrimmed,
                                                         kuerzel,
                                                         beschreibungTrimmed,
                                                         datumZeitJetzt,
                                                         datumZeitJetzt,
                                                         true);
        _kafkaSender.sendeUrlDefinition(kud);

        // URL-Definition in eigener Datenbank anlegen
        boolean warErfolgreich = _datenbank.neueKurzUrl( urlLangTrimmed, kuerzel, beschreibungTrimmed, passwort, datumZeitJetzt);
        if (warErfolgreich) {

            return new KuerzelUndPasswort(kuerzel, passwort);

        } else {

            throw new ShortLinkException("Datenbankfehler beim Anlegen von URL-Definition für folgende URL-Definition: " +
                                         urlLangTrimmed, true);
        }
    }

}
