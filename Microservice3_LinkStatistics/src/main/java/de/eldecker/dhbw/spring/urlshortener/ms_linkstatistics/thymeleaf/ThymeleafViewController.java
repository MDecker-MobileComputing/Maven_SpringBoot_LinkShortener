package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.thymeleaf;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.ErfolgStatsFuerKuerzel;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.StatFuerMehrereZeitraeume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller, der die Anfragen für die Thymeleaf-Views bearbeitet.
 */
@Controller
public class ThymeleafViewController {

    private Logger LOG = LoggerFactory.getLogger( ThymeleafViewController.class );

    /** Datenbank-Bean, das die DB-Abfragen ausführt. */
    private Datenbank _datenbank;


    /**
     * Konstruktor, der die Abhängigkeiten injiziert.
     */
    @Autowired
    public ThymeleafViewController(Datenbank db) {

        _datenbank = db;
    }


    /**
     * Seite mit Link-Statistiken für ein bestimmtes URL-Kürzel anzeigen.
     *
     * @param kuerzel URL-Kürzel, für das die Usage-Statistiken angezeigt
     *                werden sollen
     *
     * @param model Model, in das die Daten für die Template-Datei geschrieben werden
     *
     * @return Name der Template-Datei, die angezeigt werden soll;
     *         wird in ordner {@code src/main/resources/templates/} gesucht.
     *         Wenn keine Zahlen für {@code kuerzel} gefunden werden, dann wird das
     *         Template {@code nicht_gefunden.html} angezeigt, ansonten das Template
     *         {@code link_stats.html}.
     */
    @GetMapping("/ustats/{kuerzel}")
    public String kuerzelAufloesen( @PathVariable String kuerzel,
                                    Model model ) {

        final String kuerzelTrimmed = kuerzel.trim();
        model.addAttribute("kuerzel", kuerzelTrimmed);

        LOG.info( "Versuche Usage-Stats fuer Kuerzel \"{}\" zu finden.", kuerzelTrimmed );

        // DB-Abfrage 1: Anzahl Erfolge und Misserfolge
        ErfolgStatsFuerKuerzel erfolgStats = _datenbank.calcErfolgStatsFuerKuerzel(kuerzelTrimmed);
        if (erfolgStats.istLeer()) {

            LOG.warn( "Keine Usage-Stats fuer Kuerzel \"{}\" gefunden.", kuerzelTrimmed );
            return "nicht_gefunden";
        }
        model.addAttribute("anzahl_erfolg"     , erfolgStats.anzahlErfolg()    );
        model.addAttribute("anzahl_kein_erfolg", erfolgStats.anzahlKeinErfolg());

        // DB-Abfrage 2: Anzahl Erfolge für verschiedene Zeiträume
        StatFuerMehrereZeitraeume zeitraumStats = _datenbank.calcStatsFuerZeitraeume(kuerzelTrimmed);
        model.addAttribute("anzahl_letzte_24h", zeitraumStats.anzahl1Tag() );
        model.addAttribute("anzahl_letzte_7T" , zeitraumStats.anzahl7Tage() );
        model.addAttribute("anzahl_letzte_30T", zeitraumStats.anzahl30Tage());

        return "link_stats";
    }

}