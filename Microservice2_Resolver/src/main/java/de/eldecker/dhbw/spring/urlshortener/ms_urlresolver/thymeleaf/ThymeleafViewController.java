package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.thymeleaf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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

    /**
     * Instanzname aus Konfigurationsdatei {@code application-XXX.properties};
     * wird auf Ergebnis-Seiten angezeigt, damit man Load-Balancing nachvollziehen kann.
     */
    @Value("${de.eldecker.linkshortener.ms2.instanzname}")
    private String _instanzname;


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

        model.addAttribute("instanzname" , _instanzname           );
        model.addAttribute("kuerzel"     , kuerzelTrimmed         );
        model.addAttribute("gefunden"    , true                  );
        model.addAttribute("urlLang"     , "https://www.heise.de" );
        model.addAttribute("beschreibung", "Lorem Ipsum tralala"  );

        return "ergebnis";
    }

}