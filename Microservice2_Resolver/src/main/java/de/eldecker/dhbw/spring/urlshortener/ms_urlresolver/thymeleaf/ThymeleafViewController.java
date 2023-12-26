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
     * @param model Model-Objekt, das an das Template übergeben wird
     *
     * @param kuerzel Pfadvariable aus der URL, enthält das aufzulösende Kürzel
     *
     * @return Name der aufzurufenden Template-Datei
     */
    @RequestMapping("/k/{kuerzel}")
    public String kuerzelAufloesen(Model model, @PathVariable String kuerzel) {

        model.addAttribute("instanzname", _instanzname);
        model.addAttribute("gefunden", false);

        return "aufgeloest"; // Template-Datei aufgeloest.html
    }

}