package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

import java.util.Date;

/**
 * Objekte dieser Klasse werden über Kafka an andere Microservices verteilt,
 * damit diese die URL-Definitionen in ihren lokalen Datenbanken anlegen und
 * aktualisieren können.
 */
public record KafkaUrlDefinition( int id,
                                  String originalUrl,
                                  String kuerzel,
                                  String beschreibung,
                                  Date erzeugtAm,
                                  Date letzteAenderungAm,
                                  boolean istAktiv
                                ) {
}
