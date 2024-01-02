package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * Hilfsklasse für die Arbeit mit Datum und Uhrzeit.
 */
public class DatumHelferlein {


    /**
     * Berechnet ein {@code Date}-Objekt, das {@code stunden} Stunden vor der aktuellen
     * Systemzeit liegt.
     *
     * @param stunden Anzahl der Stunden, die von der aktuellen Systemzeit abgezogen werden 
     *                sollen.
     */
    public static Date berechneHeuteMinusStunden(int stunden) {

        LocalDateTime localDateTime = LocalDateTime.now().minusHours(stunden);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        long epochMillis = localDateTime.atZone(defaultZoneId).toInstant().toEpochMilli();
        return new Date(epochMillis);
    }

}