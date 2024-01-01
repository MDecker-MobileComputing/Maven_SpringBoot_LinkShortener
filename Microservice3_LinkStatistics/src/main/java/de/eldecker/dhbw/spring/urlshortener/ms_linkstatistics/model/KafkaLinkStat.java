package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Diese Klasse repräsentiert eine Nachricht, die von der
 * Microservice-2-Komponente verschickt wird, wenn ein URL-Kürzel
 * aufgelöst wurde. Es ist kein Attribut für den User-Agent
 * (Browser-Kennung) enthalten, weil das nicht für die Statistik
 * benötigt wird.
 * <br><br>
 *
 * Mit den {@code @JsonProperty}-Annotationen wird festgelegt, wie
 * die Attribute in der JSON-Nachricht heißen sollen, damit alle
 * relevanten Attribute der Kafka-Nachricht von Microservice 2
 * in ein Objekt dieser Klasse deserialisiert werden können.
 */
public record KafkaLinkStat( @JsonProperty("zeitpunkt")
                             Date datumUndZeit,

                             @JsonProperty("urlKuerzel")
                             String kuerzel,

                             @JsonProperty("konnteAufgeloestWerden")
                             boolean erfolgreich
                           ) {
}
