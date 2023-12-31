package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.LinkZugriffEntity;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.ErfolgStatsFuerKuerzel;

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
     * <a href="https://jakarta.ee/specifications/platform/9/apidocs/jakarta/persistence/entitymanager">Offizielle Doku</a>
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
     * Speichert einen Link-Zugriff in der Datenbank. Als Zeitpunkt wird
     * die aktuelle Systemzeit verwendet.
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
}
