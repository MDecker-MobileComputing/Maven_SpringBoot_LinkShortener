package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Die zugehörigen Template-Dateien finden sich im Ordner {@code src/main/resources/templates}.
 * Der Name des anzuzeigenden Template wird von den Methoden dieser Klasse als String zurückgegeben.
 */
@Controller
public class ThymeleafViewController {


    /**
     * HTTP-Get-Methode für das Auflösen eines URL-Kürzels.
     *
     * @param model Model-Objekt, das an das Template übergeben wird
     *
     * @return Name der aufzurufenden Template-Datei
     */
    @GetMapping("/k")
    public String kuerzelAufloesen(Model model) {

        model.addAttribute("message", "Lorem Ipsum");
        return "aufgeloest"; // Template-Datei aufgeloest.html
    }

}