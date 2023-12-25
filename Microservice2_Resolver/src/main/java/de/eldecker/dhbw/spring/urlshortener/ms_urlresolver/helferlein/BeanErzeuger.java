package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;


/**
 * Die Klasse ist mit {@code Configuration} annotiert, weil sie mit {@code @Bean}
 * annotierte Methoden hat, die Objekte für Dependency Injection bereitstellen.
 */
@Configuration
public class BeanErzeuger {

    /**
     * Liefert konfiguriertes ObjectMapper-Objekt zurück, welches für Object-nach-JSON (Serialisierung)
     * oder JSON-nach-Objekt (Deserialisierung) benötigt wird.
     * <br><br>
     *
     * Konfiguration:
     * <ul>
     * <li>Kein Fehler, wenn beim Deserialisierung ein Feld im JSON gefunden wird, das nicht in der Zielklasse
     *     definiert ist</li>
     *  <li>Zeitstempelwerte werden im ISO-8601-Format im JSON abgelegt;
     *      Beispiel: {@code 2022-03-14T15:09:26+00:00} (wegen {@code +00:00}  ist das Datum in UTC})
     *  </li>
     *  <li>Das erzeugte JSOn wird für bessere Lesbarkeit durch Einrückungen formatiert.</li>
     * </ul>
     *
     * @return Konfigurierter Object-Mapper
     */
    @Bean
    public ObjectMapper erzeugeObjectMapper() {

        return JsonMapper.builder()
                .disable(FAIL_ON_UNKNOWN_PROPERTIES) // Ignoriert unbekannte JSON-Felder beim Deserialisieren
                .disable(WRITE_DATES_AS_TIMESTAMPS) // Schreibt Datum und Zeit im ISO-8601-Format
                .enable(INDENT_OUTPUT) // Schöne Ausgabe beim Serialisieren von Objekten in JSON
                .build();
    }

}