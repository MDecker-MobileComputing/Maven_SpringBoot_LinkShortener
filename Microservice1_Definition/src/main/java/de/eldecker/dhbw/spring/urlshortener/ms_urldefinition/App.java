package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Klasse mit Einstiegspunkt der Anwendung.
 */
@SpringBootApplication
public class App {

    /**
     * Einstiegsmethode der Anwendung.
     *
     * @param args Kommandozeilenargumente, werden an Spring Boot weitergegeben.
     */
	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

}
