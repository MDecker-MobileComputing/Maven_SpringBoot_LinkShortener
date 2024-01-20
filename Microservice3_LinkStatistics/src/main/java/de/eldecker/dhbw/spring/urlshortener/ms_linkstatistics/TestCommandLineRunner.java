package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class TestCommandLineRunner implements CommandLineRunner {

    private Logger LOG = LoggerFactory.getLogger( TestCommandLineRunner.class );

    @Autowired
    private Datenbank _db;

    /**
     * Diese Methode fügt Beispiel-Records in die Tabelle ein.
     * Sie wird unmittelbar nach dem Start der Applikation ausgeführt.
     */
    @Override
    public void run(String... args) throws Exception {

        Date jetztDate = new Date();

        _db.speichereLinkZugriff("test1", false, jetztDate);
        _db.speichereLinkZugriff("test2", true,  jetztDate);

        LOG.info("Zwei Datensaetze in DB eingefuegt.");
    }

}
