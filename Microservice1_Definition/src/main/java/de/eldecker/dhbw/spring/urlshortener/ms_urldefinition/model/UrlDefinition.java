package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

import java.util.Date;

/**
 * Record-Klasse mit allen Feldern der DB-Tabelle {@code URLS}.
 */
public record UrlDefinition( int id,
                             String originalUrl,
                             String kuerzel,
                             String beschreibung,
                             Date erzeugtAm,
                             Date letzteAenderungAm,
                             boolean istAktiv,
                             String passwort ) {
}

