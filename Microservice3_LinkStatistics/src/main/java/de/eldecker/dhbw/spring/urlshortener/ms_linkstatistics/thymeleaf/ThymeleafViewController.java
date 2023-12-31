package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.thymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ThymeleafViewController {

    private Logger LOG = LoggerFactory.getLogger( ThymeleafViewController.class );

    /**
     * Seite mit Link-Statistiken für ein bestimmtes URL-Kürzel anzeigen.
     *
     * @param kuerzel URL-Kürzel, für das die Usage-Statistiken angezeigt
     *                werden sollen
     *
     * @param model Model, in das die Daten für die Template-Datei geschrieben werden
     *
     * @return Name der Template-Datei, die angezeigt werden soll;
     *         wird in ordner {@code src/main/resources/templates/} gesucht
     */
    @GetMapping("/ustats/{kuerzel}")
    public String kuerzelAufloesen( @PathVariable String kuerzel,
                                    Model model ) {

        final String kuerzelTrimmed = kuerzel.trim();

        LOG.info( "Versuche Usage-Stats fuer Kuerzel \"{}\" zu finden.", kuerzelTrimmed );

        model.addAttribute("kuerzel", kuerzelTrimmed);

        return "nicht_gefunden";
    }

}