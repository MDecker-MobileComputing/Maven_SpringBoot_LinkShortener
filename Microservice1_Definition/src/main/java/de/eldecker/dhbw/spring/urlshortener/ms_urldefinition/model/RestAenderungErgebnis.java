package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Ein Objekt dieser Record-Klasse wird von REST-Endpunkten als Ergebnis zurückgegeben.
 *
 * @param erfolgreich {@code true}, falls die Aenderung erfolgreich war; sonst {@code false}
 *                    (z.B. weil das Kuerzel nicht existiert oder das Passwort falsch ist)
 *
 * @param fehlermeldung Fehlermeldung, falls die Aenderung nicht erfolgreich war; sonst leer
 */
public record RestAenderungErgebnis( boolean erfolgreich,
                                     String fehlermeldung ) {
}
