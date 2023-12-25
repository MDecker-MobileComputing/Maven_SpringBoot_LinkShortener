package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.db.Datenbank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@Sql(scripts = {"/loeschen.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DatenbankTest {

	/** Class/Code under Test (CUT) */
	@Autowired
	private Datenbank _cut;


	/**
	 * Es sollten nur die Datensätze aus {@code data.sql} gezählt werden.
	 */
	@Test
	void anzahlErmitteln() {

		int anzahl = _cut.holeAnzahl();

		assertEquals(2, anzahl);
	}


	@Test
	void urlDefinitionEinfuegen() {

		final String urlOriginal  = "https://www.test.com/pfad1?key=1&key=2";
		final String urlKuerzel   = "abc42";
		final String beschreibung = "Lorem Ipsum";
		final String passwort     = "geheim";
		final Date   jetztDate    = new Date();

		// Aufruf der Methode unter Test
		boolean erfolgreich = _cut.neueKurzUrl(urlOriginal, urlKuerzel, beschreibung, passwort, jetztDate);

		assertTrue(erfolgreich);

		int anzahlNachher = _cut.holeAnzahl();

		assertEquals(3, anzahlNachher);
	}

}
