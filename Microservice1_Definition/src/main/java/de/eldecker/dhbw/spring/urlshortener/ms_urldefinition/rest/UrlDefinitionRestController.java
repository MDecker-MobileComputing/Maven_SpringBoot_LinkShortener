package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.rest;

import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnlegenErgebnisRecord.baueErfolgRecord;
import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnlegenErgebnisRecord.baueFehlerRecord;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnlegenErgebnisRecord;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnzahlRecord;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.validator.routines.UrlValidator;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * REST-Controller mit den REST-Endpunkten für die URL-Definitionen.
 * <br><br>
 *
 * Die REST-Methoden haben zusätzliche Annotationen (z.B. @Operation, @ApiResponses),
 * um zusätzliche Informationen für die automatische Generierung der API-Dokumentation
 * durch Swagger bereitzustellen.
 */
@RestController
@RequestMapping("/urldef/v1")
public class UrlDefinitionRestController {

    private static Logger LOG = LoggerFactory.getLogger(UrlDefinitionRestController.class);

    /** Bean für Zugriff auf Datenbank */
    private Datenbank _datenbank;

    /** Bean für Validierung der Lang/Original-URL. */
    private UrlValidator _urlValidator;


    /**
     * Konstruktor für Dependency Injection.
     */
    public UrlDefinitionRestController(Datenbank db,
                                       UrlValidator urlValidator) {
        _datenbank    = db;
        _urlValidator = urlValidator;
    }

    /**
     * REST-Endpunkt für HTTP-GET, um die Anzahl der in der Datenbank gespeicherten URL-Definitionen abzufragen.
     *
     * @return Anzahl der in der Datenbank gespeicherten URL-Definitionen oder -1 bei Fehler;
     *         HTTP-Status 200 bei Erfolg, 500 bei Fehler.
     */
    @Operation(summary = "Holt die Gesamtzahl der gespeicherten URL-Definitionen",
               description = "Auch inaktive oder abgelaufene URL-Definitionen werden gezählt (einfach alle Datensätze in der Tabelle).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anzahl erfolgreich ermittelt"),
        @ApiResponse(responseCode = "500", description = "Fehler beim Ermitteln der Anzahl")
    })
    @GetMapping("/anzahl")
    public ResponseEntity<RestAnzahlRecord> getAnzahl() {

        int anzahl = _datenbank.holeAnzahl();
        RestAnzahlRecord ergebnisRecord = new RestAnzahlRecord(anzahl);
        if (anzahl >= 0) {

            return ResponseEntity.status(OK).body(ergebnisRecord);

        } else {

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ergebnisRecord);
        }
    }

    /**
     * REST-Endpunkt für HTTP-Post, um eine neue URL-Definition anzulegen.
     *
     * @param urlLang Original-URL
     * @param beschreibung Beschreibungstext
     * @return Ergebnis des Anlegens einer URL-Definition, bei Erfolg mit Küzel und Passwort;
     *         HTTP-Status 201 bei Erfolg, 400 bei Fehler.
     */
    @Operation(summary = "Neue URL-Definition anlegen",
               description = "Die URL-Definition wird mit dem Status 'aktiv' angelegt.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "URL-Definition wurde erfolgreich angelegt"),
        @ApiResponse(responseCode = "400", description = "URL-Definition konnte nicht angelegt werden")
    })
    @PostMapping("/anlegen")
    public ResponseEntity<RestAnlegenErgebnisRecord> neueKurzUrlAnlegen(@RequestParam String urlLang,
                                                                        @RequestParam String beschreibung) {
        RestAnlegenErgebnisRecord ergebnisRecord = null;

        final String urlLangTrimmed = urlLang.trim();
        final String beschreibungTrimmed = beschreibung.trim();

        if (_urlValidator.isValid(urlLangTrimmed) == false) {

            final String fehlermeldung = "Ungültige Lang/Original-URL eingegeben: " + urlLangTrimmed;
            LOG.error(fehlermeldung);
            ergebnisRecord = baueFehlerRecord(fehlermeldung);
            return ResponseEntity.status(BAD_REQUEST).body(ergebnisRecord);
        }

        final String kuerzel = "xx";
        final String passwort = "abc-123";

        boolean warErfolgreich = _datenbank.neueKurzUrl(urlLangTrimmed, kuerzel, beschreibungTrimmed, passwort);
        if (warErfolgreich) {

            ergebnisRecord = baueErfolgRecord(kuerzel, passwort);
            return ResponseEntity.status(CREATED).body(ergebnisRecord);

        } else {

            LOG.error("Datenbankfehler beim Anlegen von URL-Definition für folgende URL-Definition: " + urlLangTrimmed);

            final String fehlermeldung = "URL-Definition für folgende URL konnte wegen DB-Fehler nicht angelegt werden: " +
                                         urlLangTrimmed;
            LOG.error(fehlermeldung);
            ergebnisRecord = baueFehlerRecord(fehlermeldung);
            return ResponseEntity.status(BAD_REQUEST).body(ergebnisRecord);
        }
    }

}
