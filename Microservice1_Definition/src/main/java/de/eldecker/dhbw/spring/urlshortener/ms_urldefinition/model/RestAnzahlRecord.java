package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Record-Klasse für die Anzahl der Datensätze in der DB-Tabelle {@code urls},
 * wird für HTTP-Antworten nach JSON serialisiert.
 *
 * @param anzahl Gesamtanzahl der Datensätze in der DB-Tabelle {@code urls}.
 */
public record RestAnzahlRecord(int anzahl) {}
