package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class TestCommandLineRunner implements CommandLineRunner {

    @Autowired
    private Datenbank _db;

    @Override
    public void run(String... args) throws Exception {

        _db.speichereLinkZugriff("test1", false, new Date());
        _db.speichereLinkZugriff("test2", true,  new Date());
    }

}
