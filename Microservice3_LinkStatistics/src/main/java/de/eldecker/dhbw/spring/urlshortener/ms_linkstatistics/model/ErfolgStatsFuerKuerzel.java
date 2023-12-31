package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model;


/**
 * Ergebnis der DB-Abfrage in Method {@link Datenbank#ermittleErfolgStatsFuerKuerzel(String)}.
 *
 * @param kuerzel Das URL-Kürzel, für das die Statistik ermittelt wurde.
 *
 * @param anzahlErfolg Anzahl der erfolgreichen Auflösungen für das Kürzel.
 *
 * @param anzahlKeinErfolg Anzahl der erfolglosen Auflösungen für das Kürzel.
 */
public record ErfolgStatsFuerKuerzel ( String kuerzel,
                                       long anzahlErfolg,
                                       long anzahlKeinErfolg ) {
}
