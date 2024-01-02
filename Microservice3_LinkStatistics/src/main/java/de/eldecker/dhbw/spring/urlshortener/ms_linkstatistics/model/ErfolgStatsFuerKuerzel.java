package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;

/**
 * Ergebnis der DB-Abfrage in Method {@link Datenbank#calcErfolgStatsFuerKuerzel(String)}.
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

    /**
     * Gibt {@code true} zurück, wenn die Statistik leer ist, d.h. wenn
     * {@link #anzahlErfolg()} und {@link #anzahlKeinErfolg()} beide {@code 0} sind.
     *
     * @return {@code true} genau dann, wenn beiden Zahlen 0 sind.
     */
    public boolean istLeer() {

        return anzahlErfolg == 0 && anzahlKeinErfolg == 0;
    }
}
