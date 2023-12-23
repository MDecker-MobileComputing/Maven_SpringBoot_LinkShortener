package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Ein Objekt dieser Klasse wird zurückgegeben, wenn das Anlegen einer URL-Definition erfolgreich war.
 * 
 * @parm erfolgreich {@code true} wenn das Anlegen erfolgreich war, sonst {@code false} 
 * @param kuerzel Kürzel der angelegten URL-Definition (nur gefüllt, wenn {@code erfolgreich == true})
 * @param passwort Passwort, das zum Ändern der URL-Definition verwendet werden kann 
 *                 (nur gefüllt, wenn {@code erfolgreich == true})
 */
public record RestAnlegenErgebnisRecord(boolean erfolgreich, String kuerzel, String passwort) {    
}
