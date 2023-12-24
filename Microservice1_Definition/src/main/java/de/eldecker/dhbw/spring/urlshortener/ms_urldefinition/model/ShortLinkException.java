package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model;

/**
 * Eigene Ausnahmeklasse für Fehler beim Anlegen einer neuen URL-Definition.
 * <br><br>
 *
 * Es wird zwischen internen und externen Fehler unterschieden. Interne Fehler
 * sind z.B. Datenbankfehler, externe Fehler sind z.B. ungültige URL vom Nutzer.
 * Interne Fehler sollten auf HTTP-Statuscode 500 (Internal Server Error) abgebildet werden,
 * externe Fehler auf 400 (Bad Request).
 */
public class ShortLinkException extends Exception {

    /**
     * Wenn {@code true}, dann ist es ein interner Fehler (z.B. Datenbankfehler),
     * sonst ein externer Fehler (z.B. ungültige URL von Nutzer eingegeben).
     */
    private boolean _istInternerFehler;

    /**
     * Ausnahme-Objekt mit Fehlerbeschreibung und extern/intern anlegen.
     *
     * @param message Fehlerbeschreibung
     * @param istInternerFehler {@code true}, wenn ein interner Fehler aufgetreten ist (z.B. DB-Fehler),
     *                          {@code false}, wenn der Fehler durch falsche Nutzereingaben
     *                          (z.B. ungültige URL) verursacht wurde. Interne Fehler sollten auf
     *                          HTTP-Statuscode 500 (Internal Server Error) abgebildet werden,
     *                          externe Fehler auf 400 (Bad Request).
     */
    public ShortLinkException(String message, boolean istInternerFehler) {

        super(message);

        _istInternerFehler = istInternerFehler;
    }


    /**
     * Gibt zurück, ob der Fehler ein interner oder externer Fehler ist.
     *
     * @return {@code true}, wenn ein interner Fehler aufgetreten ist (z.B. DB-Fehler),
     *         sonst {@code false}.
     */
    public boolean istInternerFehler() {

        return _istInternerFehler;
    }


    /**
     * Gibt zurück, ob der Fehler ein externer oder interner Fehler ist.
     *
     * @return {@code true}, wenn ein externer Fehler aufgetreten ist
     *         (z.B. ungültige URL von Nutzer eingegeben), sonst {@code false}.
     */
    public boolean istExternerFehler() {

        return !_istInternerFehler;
    }


    /**
     * Gibt die Fehlerbeschreibung zurück.
     *
     * @return Fehlerbeschreibung mit "Interner Fehler" oder "Externer Fehler" vorangestellt.
     */
    @Override
    public String getMessage() {

        if (_istInternerFehler) {

                return "Interner Fehler: " + super.getMessage();

        } else {

                return "Externer Fehler: " + super.getMessage();
        }
    }
}
