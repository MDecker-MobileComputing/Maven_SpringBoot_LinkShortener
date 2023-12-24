package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Ein Objekt dieser Klasse wird zurückgegeben, wenn das Anlegen einer URL-Definition erfolgreich war.
 *
 * @param kuerzel Kürzel der angelegten URL-Definition
 *
 * @param passwort Passwort, das zum Ändern der URL-Definition verwendet werden kann
 */
public record KuerzelUndPasswort(String kuerzel, String passwort) {

}
