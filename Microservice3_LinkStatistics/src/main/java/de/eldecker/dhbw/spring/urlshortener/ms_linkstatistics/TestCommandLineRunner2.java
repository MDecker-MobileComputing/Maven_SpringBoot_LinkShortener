package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.ErfolgStatsFuerKuerzel;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.StatFuerMehrereZeitraeume;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(2)
public class TestCommandLineRunner2 implements CommandLineRunner {

    private Logger LOG = LoggerFactory.getLogger( TestCommandLineRunner2.class );

    @Autowired
    private Datenbank _db;

    @Override
    public void run(String... args) throws Exception {

        ErfolgStatsFuerKuerzel ergebnis = _db.calcErfolgStatsFuerKuerzel("test1");
        LOG.info("Gesamtzahl erfolgreicher Kuerzelaufloesungen: " + ergebnis);

        StatFuerMehrereZeitraeume statsZeitraeume = _db.calcStatsFuerZeitraeume("test1");
        LOG.info("Statistik für mehrere Zeiträume: " + statsZeitraeume);
    }

}
