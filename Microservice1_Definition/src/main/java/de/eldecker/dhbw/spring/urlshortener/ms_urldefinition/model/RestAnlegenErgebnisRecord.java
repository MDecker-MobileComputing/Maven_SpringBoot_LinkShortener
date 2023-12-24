package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Ein Objekt dieser Klasse wird zurückgegeben, wenn das Anlegen einer URL-Definition erfolgreich war.
 *
 * @param erfolgreich {@code true} wenn das Anlegen erfolgreich war, sonst {@code false}
 * @param kuerzel Kürzel der angelegten URL-Definition (nur gefüllt, wenn {@code erfolgreich == true})
 * @param passwort Passwort, das zum Ändern der URL-Definition verwendet werden kann
 *                 (nur gefüllt, wenn {@code erfolgreich == true})
 * @param fehler Fehlermeldung, wenn das Anlegen nicht erfolgreich war (nur gefüllt, wenn {@code erfolgreich == false})
 */
public record RestAnlegenErgebnisRecord(boolean erfolgreich,
                                        String kuerzel,
                                        String passwort,
                                        String fehler) {

    /**
     * Methode zum Erzeugen eines Objekts, wenn das Anlegen einer URL-Definition erfolgreich war.
     *
     * @param kuerzel Angelegtes Kürzel
     * @param passwort Passwort, das für evtl. Änderungen benötigt wird
     * @return Objekt mit {@code erfolgreich=true}, {@code kuerzel} und {@code passwort} gesetzt,
     *         {@code fehler} leer
     */
    public static RestAnlegenErgebnisRecord baueErfolgRecord(String kuerzel, String passwort) {

        return new RestAnlegenErgebnisRecord(true, kuerzel, passwort, ""); // "": leere Fehlermeldung
    }


    /**
     * Methode zum Erzeugen eines Objekts, wenn das Anlegen einer URL-Definition
     * nicht erfolgreich war.
     *
     * @param fehler Beschreibung des Fehlers
     * @return Objekt mit Fehlermeldung un {@code erfolgreich=false}
     */
    public static RestAnlegenErgebnisRecord baueFehlerRecord(String fehler) {

        return new RestAnlegenErgebnisRecord(false, "", "", fehler); // "": leeres Kürzel und leeres Passwort
    }

}
