package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db;

import static de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein.DatumHelferlein.berechneHeuteMinusStunden;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.ErfolgStatsFuerKuerzel;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.StatFuerMehrereZeitraeume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Diese Klasse kapselt den Zugriff auf die Datenbank.
 */
@Repository
public class Datenbank {

    private Logger LOG = LoggerFactory.getLogger( Datenbank.class );

    /**
     * Bean mit API für JPA; wird bei Verwendung von JPA anstelle von {@code JDBCTemplate} benötigt.
     * <br>
     * <a href="https://bit.ly/48xmIqp">Offizielle Doku</a>
     */
    private EntityManager _entityManager;


    /**
     * Konstruktor, der die Abhängigkeiten injiziert.
     */
    @Autowired
    public Datenbank(EntityManager entityManager) {

        _entityManager = entityManager;
    }


    /**
     * Speichert einen Link-Zugriff in der Datenbank. Als Zeitpunkt wird die aktuelle
     * Systemzeit verwendet.
     *
     * @param kuerzel Das Kürzel, das beim Zugriff aufgelöst werden sollte.
     *
     * @param erfolgreich {@code true} genau dann, wenn die Kurz-URL aufgelöst
     *                    werden konnte.
     *
     * @param zeitpunkt Zeitpunkt (Datum+Uhrzeit) des Zugriffs
     */
    @Transactional
    public void speichereLinkZugriff(String kuerzel, boolean erfolgreich, Date zeitpunkt) {

        LinkZugriffEntity linkZugriff = new LinkZugriffEntity(kuerzel, zeitpunkt, erfolgreich);

        try {

            _entityManager.persist(linkZugriff);
        }
        catch (PersistenceException | IllegalArgumentException ex) {

            LOG.error("Fehler beim Speichern der folgenden Entity: {}",
                      linkZugriff, ex);
        }
    }


    /**
     * Ermittelt die Anzahl der erfolgreichen und nicht-erfolgreichen Zugriffe
     * für ein bestimmtes Kürzel.
     *
     * @param kuerzel Kürzel, z.B. "ab3"
     *
     * @return Anzahl der erfolgreichen und nicht-erfolgreichen Zugriffe für {@code kuerzel}
     */
    public ErfolgStatsFuerKuerzel calcErfolgStatsFuerKuerzel(String kuerzel) {

        TypedQuery<Object[]> query =
            _entityManager.createNamedQuery("LinkZugriff.countErfolgByKuerzel", Object[].class);

        query.setParameter("kuerzel", kuerzel);

        int anzahlErfolg     = 0;
        int anzahlKeinErfolg = 0;

        List<Object[]> results = query.getResultList();

        for (Object[] result : results) {

            Boolean erfolgreich = (Boolean) result[0];
            Long    count       = (Long)    result[1];

            if (erfolgreich) {

                anzahlErfolg = count.intValue();

            } else {

                anzahlKeinErfolg = count.intValue();
            }
        }

        return new ErfolgStatsFuerKuerzel(kuerzel, anzahlErfolg, anzahlKeinErfolg);
    }


     /**
      * Ermittelt die Anzahl Zugriffe für {@code kuerzel} für verschiedene
      * Zeiträume.
      *
      * @param kuerzel URL-Kürzel, z.B. "ab3"
      */
    public StatFuerMehrereZeitraeume calcStatsFuerZeitraeume(String kuerzel) {

        TypedQuery<StatFuerMehrereZeitraeume> query =
                _entityManager.createNamedQuery("LinkZugriff.countByKuerzelAndPeriod",
                                                StatFuerMehrereZeitraeume.class);

        query.setParameter("kuerzel"      , kuerzel);
        query.setParameter("oneDayAgo"    , berechneHeuteMinusStunden(  1*24 ));
        query.setParameter("sevenDaysAgo" , berechneHeuteMinusStunden(  7*24 ));
        query.setParameter("thirtyDaysAgo", berechneHeuteMinusStunden( 30*24 ));

        return query.getSingleResult();
    }

}
