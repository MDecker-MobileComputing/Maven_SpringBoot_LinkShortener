package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import static de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka.KafkaTopics.TOPIC_URL_DEFINITION;

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


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KafkaEmpfaenger(ObjectMapper objectMapper) {

        _objectMapper = objectMapper;
    }


    /**
     * Diese Methode wird aufgerufen, wenn eine Kafka-Nachricht über eine neue
     * oder aktualisierte URL-Definition empfangen wurde.
     *
     * @param jsonString JSON-String mit URL-Definition
     */
    @KafkaListener(id = "mein-kafka-listener-1", topics = TOPIC_URL_DEFINITION)
    public void onNachrichtEmpfangen(String jsonString) {

        try {

            KafkaShortLink shortLinkReceived = _objectMapper.readValue(jsonString, KafkaShortLink.class);

            LOG.info("Kafka-Listener hat aktuelle Nachricht empfangen: \"{}\"", shortLinkReceived);
        }
        catch (JsonProcessingException ex) {

            LOG.error("Fehler beim Deserialisieren der JSON-Nachricht über neue/aktualisierte URL-Definition: {}",
                      ex.getMessage());
        }
    }

}