package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import static de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka.KafkaTopics.TOPIC_URL_DEFINITION;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.KafkaShortLink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Empfänger für Nachrichten über Kafka.
 */
@Component
public class KafkaEmpfaenger {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEmpfaenger.class);

    /** Bean für Deserialisierung von JSON nach Java-Objekt.  */
    private ObjectMapper _objectMapper;

    /** Bean für Datenbankzugriffe, z.B. Einfügen/Aktualisierungen */
    private Datenbank _datenbank;


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KafkaEmpfaenger( ObjectMapper objectMapper,
                            Datenbank datenbank       ) {

        _objectMapper = objectMapper;
        _datenbank    = datenbank;
    }


    /**
     * Diese Methode wird aufgerufen, wenn eine Kafka-Nachricht über eine neue
     * oder aktualisierte URL-Definition empfangen wurde.<br><br>
     *
     * Achtung: Ohne Zuweisung zu einer eigenen GroupId würde jede Nachricht
     * nur einer Instanz des Microservices zugestellt werden, was aber für
     * den Anwendungsfall nicht sinnvoll wäre.
     *
     * @param jsonString JSON-String mit URL-Definition
     */
    @KafkaListener(id = "mein-kafka-listener-1", topics = TOPIC_URL_DEFINITION, groupId = "${de.eldecker.linkshortener.ms2.instanzname}")
    public void onNachrichtEmpfangen(String jsonString) {

        try {

            KafkaShortLink shortLinkReceived = _objectMapper.readValue(jsonString, KafkaShortLink.class);

            boolean erfolg = _datenbank.shortLinkEinfuegenOderAktualisieren(shortLinkReceived);
            if (erfolg) {

                LOG.info("Kafka-Listener hat aktuelle Nachricht empfangen, wurde in DB eingefügt/aktualisiert: \"{}\"",
                         shortLinkReceived);
            } else {

                LOG.error("Kafka-Listener hat aktuelle Nachricht empfangen, konnte aber nicht in DB eingefügt/aktualisiert werden: \"{}\"",
                          shortLinkReceived);
            }
        }
        catch (JsonProcessingException ex) {

            LOG.error("Fehler beim Deserialisieren der JSON-Nachricht über neue/aktualisierte URL-Definition: {}",
                      ex.getMessage());
        }
    }

}