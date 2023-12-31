package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db;


/**
 * Ergebnis der DB-Abfrage in Method {@link Datenbank#ermittleErfolgStatsFuerKuerzel(String)}.
 */
public record ErfolgStatsFuerKuerzel ( String kuerzel,
                                       long anzahlErfolg,
                                       long anzahlKeinErfolg ) {
}
