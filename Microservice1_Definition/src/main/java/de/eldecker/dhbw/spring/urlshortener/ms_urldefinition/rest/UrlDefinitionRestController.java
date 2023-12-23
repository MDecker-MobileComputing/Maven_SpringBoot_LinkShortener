package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnlegenErgebnisRecord;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnzahlRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST-Controller mit den REST-Endpunkten für die URL-Definitionen.
 */
@RestController
@RequestMapping("/urldef/v1")
public class UrlDefinitionRestController {
    
    private static Logger LOG = LoggerFactory.getLogger(UrlDefinitionRestController.class);

    /** Bean für Zugriff auf Datenbank */
    private Datenbank _datenbank;


    /**
     * Konstruktor für Dependency Injection
     */
    public UrlDefinitionRestController(Datenbank db) {
        
        _datenbank = db;
    }

    /**
     * REST-Endpunkt für HTTP-GET, um die Anzahl der in der Datenbank gespeicherten URL-Definitionen abzufragen.
     * 
     * @return Anzahl der in der Datenbank gespeicherten URL-Definitionen oder -1 bei Fehler;
     *         HTTP-Status 200 bei Erfolg, 500 bei Fehler.
     */
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
    @PostMapping("/anlegen")
    public ResponseEntity<RestAnlegenErgebnisRecord> neueKurzUrlAnlegen(@RequestParam String urlLang, 
                                                                        @RequestParam String beschreibung) {                                                                        
        RestAnlegenErgebnisRecord ergebnisRecord = null;

        boolean warErfolgreich = _datenbank.neueKurzUrl(urlLang, "xx", beschreibung, "geheim-123", false );
        if (warErfolgreich) {

            ergebnisRecord = new RestAnlegenErgebnisRecord(true, "kuerzel", "passwort");
            return ResponseEntity.status(CREATED).body(ergebnisRecord);

        } else {

            ergebnisRecord = new RestAnlegenErgebnisRecord(false, "", "");
            return ResponseEntity.status(BAD_REQUEST).body(ergebnisRecord);
        }                
    }

}
