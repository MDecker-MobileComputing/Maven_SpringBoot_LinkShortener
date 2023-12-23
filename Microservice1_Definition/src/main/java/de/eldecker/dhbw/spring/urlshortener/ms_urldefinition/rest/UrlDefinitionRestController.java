package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.rest;

import static org.springframework.http.HttpStatus.OK;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.RestAnzahlRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/anzahl")
    public ResponseEntity<RestAnzahlRecord> getAnzahl() {

        LOG.info("Aufruf von getAnzahl()");

        int anzahl = _datenbank.getAnzahl();
        RestAnzahlRecord ergebnisRecord = new RestAnzahlRecord(anzahl);

        return ResponseEntity.status(OK).body(ergebnisRecord);
    }

}
