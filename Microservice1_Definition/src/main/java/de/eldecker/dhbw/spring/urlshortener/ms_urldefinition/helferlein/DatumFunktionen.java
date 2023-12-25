package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.helferlein;

import java.sql.Timestamp;
import java.util.Date;

public class DatumFunktionen {

    /**
     * Methode zum Umwandeln eines Timestamps in ein Date-Objekt.
     *
     * @param timestamp Timestamp-Objekt, das umgewandelt werden soll
     * @return Date-Objekt
     */
    public static Date timestamp2Date(Timestamp timestamp) {

        return Date.from( timestamp.toInstant() );
    }
}
