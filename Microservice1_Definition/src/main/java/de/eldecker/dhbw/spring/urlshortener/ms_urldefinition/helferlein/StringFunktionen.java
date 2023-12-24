package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein;

import static de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein.StringFunktionen.erzeugePasswort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Klasse mit statischen Funktionen zur Arbeit mit Strings.
 */
public class StringFunktionen {

    private static Logger LOG = LoggerFactory.getLogger(StringFunktionen.class);

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
     * @return String-Repräsentation von {@code zahl}; ist leerer String, falls {@code zahl <= 0}
     */
    public static String zahlZuString(int zahl) {

        // Zeichenvorrat ohne "0"/"O", "l"/"1" und "5"/"S" (wegen Verwechslungsgefahr)
        final String ZEICHENVORRAT = "abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ23456789";
        final int    ZEICHENVORRAT_LAENGE = ZEICHENVORRAT.length();

        if (zahl <= 0) {

            LOG.warn("Methode zahlZuString() aufgerufen mit Zahl <= 0 als Argument: " + zahl);
            return "";

        } else {

            StringBuilder result = new StringBuilder();

            while (zahl > 0) {

                int rest = zahl % ZEICHENVORRAT_LAENGE;
                if (rest == 0) {

                    rest = ZEICHENVORRAT_LAENGE;
                    zahl--;
                }

                result.insert(0, ZEICHENVORRAT.charAt(rest - 1));
                zahl /= ZEICHENVORRAT_LAENGE;
            }

            return result.toString();
        }
    }

}