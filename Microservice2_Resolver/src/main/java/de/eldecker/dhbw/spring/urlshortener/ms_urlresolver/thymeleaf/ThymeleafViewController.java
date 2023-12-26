package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.thymeleaf;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.AufgeloesterLink;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Die zugehörigen Template-Dateien finden sich im Ordner {@code src/main/resources/templates}.
 * Der Name des anzuzeigenden Template wird von den Methoden dieser Klasse als String zurückgegeben.
 * <br><br>
 *
 * Wenn in der Template-Datei ein Fehler ist, dann wird erst zur Laufzeit beim ersten Aufruf dieser
 * Seite eine Fehlermeldung im Log angezeigt.
 */
@Controller
public class ThymeleafViewController {

    private Logger LOG = LoggerFactory.getLogger(ThymeleafViewController.class);

    /**
     * Instanzname aus Konfigurationsdatei {@code application-XXX.properties};
     * wird auf Ergebnis-Seiten angezeigt, damit man Load-Balancing nachvollziehen kann.
     */
    @Value("${de.eldecker.linkshortener.ms2.instanzname}")
    private String _instanzname;

    /** Bean für Zugriff auf Datenbank. */
    private Datenbank _datenbank;


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public ThymeleafViewController(Datenbank datenbank) {

        _datenbank = datenbank;
    }


    /**
     * HTTP-Get-Methode für das Auflösen eines URL-Kürzels.
     *
     * @param kuerzel Pfadvariable aus der URL, enthält das aufzulösende URL-Kürzel
     *
     * @param model Model-Objekt, das an das Template übergeben wird
     *
     * @return Name der aufzurufenden Template-Datei "ergebnis.html" aus
     *         Verzeichnis {@code src/main/resources/templates}
     */
    @GetMapping("/k/{kuerzel}")
    public String kuerzelAufloesen(@PathVariable String kuerzel, Model model) {

        final String kuerzelTrimmed = kuerzel.trim();

        model.addAttribute("kuerzel"     , kuerzelTrimmed);
        model.addAttribute("instanzname" , _instanzname  );

        Optional<AufgeloesterLink> ergebnisOptional = _datenbank.kuerzelAufloesen(kuerzelTrimmed);
        if (ergebnisOptional.isEmpty()) {

            LOG.warn("Kein Datensatz mit kuerzel {} gefunden", kuerzelTrimmed);

            model.addAttribute("gefunden", false );

        } else {

            AufgeloesterLink al = ergebnisOptional.get();

            if (al.istAktiv()) {

                model.addAttribute("gefunden"           , true );
                model.addAttribute("urlLang"            , al.urlOriginal()    );
                model.addAttribute("beschreibung"       , al.beschreibung()   );
                model.addAttribute("zeitpunkt_erzeugung", al.zeitpunktErzeugung() );

                String letzteAenderung = "–";
                if (!al.zeitpunktAktualisierung().equals(al.zeitpunktErzeugung())) {

                    letzteAenderung = al.zeitpunktAktualisierung();
                }

                model.addAttribute("zeitpunkt_aenderung", letzteAenderung );

            } else {

                // Link wurde gefunden, ist aber nicht aktiv, also tun wir so, als wäre er nicht da.
                model.addAttribute("gefunden", false );

                LOG.info("Link fuer Kuerzel \"{}\" gefunden, aber ist nicht aktiv, also nicht anzeigen.",
                                kuerzelTrimmed);
            }
        }

        return "ergebnis";
    }

}