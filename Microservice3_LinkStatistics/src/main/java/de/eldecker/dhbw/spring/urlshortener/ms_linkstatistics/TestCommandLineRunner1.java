package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class TestCommandLineRunner1 implements CommandLineRunner {

    private Logger LOG = LoggerFactory.getLogger( TestCommandLineRunner1.class );

    @Autowired
    private Datenbank _db;

    @Override
    public void run(String... args) throws Exception {

        Date jetztDate = new Date();

        _db.speichereLinkZugriff("test1", false, jetztDate);
        _db.speichereLinkZugriff("test2", true,  jetztDate);

        LOG.info("Zwei Datensaetze in DB eingefuegt.");
    }

}
