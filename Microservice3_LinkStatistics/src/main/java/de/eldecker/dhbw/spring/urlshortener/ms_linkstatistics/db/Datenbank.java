package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.Date;

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
     * Bean mit API für JPA.
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

        LinkZugriff linkZugriff = new LinkZugriff(kuerzel, zeitpunkt, erfolgreich);

        try {

            _entityManager.persist(linkZugriff);
        }
        catch (PersistenceException | IllegalArgumentException ex) {

            LOG.error("Fehler beim Speichern der folgenden Entity: {}",
                      linkZugriff, ex);
        }
    }

}
