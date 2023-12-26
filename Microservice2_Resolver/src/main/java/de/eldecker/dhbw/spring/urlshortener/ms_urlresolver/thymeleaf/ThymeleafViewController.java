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
 */
@Controller
public class ThymeleafViewController {

    /**
     * Instanzname aus Konfigurationsdatei {@code application-XXX.properties}
     */
    @Value("${de.eldecker.linkshortener.ms2.instanzname}")
    private String _instanzname;


    /**
     * HTTP-Get-Methode für das Auflösen eines URL-Kürzels.
     *
     * @param kuerzel Pfadvariable aus der URL, enthält das aufzulösende Kürzel
     *
     * @param model Model-Objekt, das an das Template übergeben wird
     *
     * @return Name der aufzurufenden Template-Datei
     */
    @GetMapping("/k/{kuerzel}")
    public String kuerzelAufloesen(@PathVariable String kuerzel, Model model) {

        model.addAttribute("instanzname", _instanzname);
        model.addAttribute("gefunden", false);

        return "ergebnis"; // Template-Datei ergebnis.html
    }

}