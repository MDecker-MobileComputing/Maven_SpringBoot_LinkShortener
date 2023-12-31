package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model;

/**
 * Ein Objekt dieser Record-Klasse repräsentiert die Anzahl der Zugriffe auf ein Kürzel in
 * verschiedenen Zeiträumen.
 *
 * @param anzahl1Tag Anzahl der Zugriffe auf das Kürzel in den letzten 24 Stunden.
 *
 * @param anzahl7Tage Anzahl der Zugriffe auf das Kürzel in den letzten 7 Tagen.
 *
 * @param anzahl30Tage Anzahl der Zugriffe auf das Kürzel in den letzten 30 Tagen.
 */
public record StatFuerMehrereZeitraeume( Long anzahl1Tag,
                                         Long anzahl7Tage,
                                         Long anzahl30Tage ) {
}