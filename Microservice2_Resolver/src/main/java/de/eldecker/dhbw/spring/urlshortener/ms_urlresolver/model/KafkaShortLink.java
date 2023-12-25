package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


/**
 * Record-Klasse als Serialisierungsziel für von MS1 empfangenen
 * URL-Definitionen. Einzelne Attribute haben andere Namen als in der
 * JSON-Datei, weil die JSON-Datei von MS1 nicht von uns beeinflusst werden kann.
 * Der JSON-Name wird dann mit {@code @JsonProperty} angegeben.
 */
public record KafkaShortLink( int id,
                              @JsonProperty("originalUrl") String urlOriginal,
                              String kuerzel,
                              String beschreibung,
                              @JsonProperty("erzeugtAm") Date zeitpunktErzeugt,
                              @JsonProperty("letzteAenderungAm") Date zeitpunktGeaendert,
                              boolean istAktiv
                            ) {
}
