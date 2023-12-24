package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein;

import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein.StringFunktionen.erzeugePasswort;


/**
 * Klasse mit statischen Funktionen zur Arbeit mit Strings.
 */
public class StringFunktionen {

    /**
     * Methode zum Erzeugen eines zufälligen Passwortes.
     *
     * @return Passwort mit 12 Zeichen, ohne Zeichen mit Verwechslungsgefahr
     */
    public static String erzeugePasswort() {

        // Zeichenvorrat für Passwort, ohne "0"/"O", "l"/"1" und "5"/"S" (wegen Verwechslungsgefahr)
        final String passwortZeichen = "ABCDEFGHIJKLMNPQRTUVWXYZabcdefghijkmnopqrstuvwxyz12346789.,!?-+*";

        final int passwortLaenge = 12;

        StringBuilder passwort = new StringBuilder(passwortLaenge);

        for (int i = 0; i < passwortLaenge; i++) {

            int index = (int)(passwortZeichen.length() * Math.random());
            passwort.append(passwortZeichen.charAt(index));
        }

        return passwort.toString();
    }

    /**
     * Methode wandelt {@code zahl} in eine Zeichenfolge um, die als Kürzel in Kurz-URL
     * verwendet werden kann.
     *
     * @param zahl Zahl größer 0, die in Zeichenfolge umgewandelt werden soll;
     *             nächste ID aus Datenbank-Tabelle {@code URLS}.
     * @return String-Repräsentation von {@code zahl}
     */
    public static String zahlZuString(int zahl) {

        // Zeichenvorrat ohne "0"/"O", "l"/"1" und "5"/"S" (wegen Verwechslungsgefahr)
        final String ZEICHENVORRAT = "abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ23456789";

        if (zahl <= 0) {

            return "";

        } else if (zahl <= ZEICHENVORRAT.length()) {

            return String.valueOf(ZEICHENVORRAT.charAt(zahl - 1));

        } else {

            int quotient  = zahl / ZEICHENVORRAT.length();
            int rest      = zahl % ZEICHENVORRAT.length();
            if (rest == 0) {

                quotient--;
                rest = ZEICHENVORRAT.length();
            }

            return zahlZuString(quotient) + zahlZuString(rest); // Rekursion!
        }
    }

}