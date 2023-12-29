package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import static de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka.KafkaTopics.TOPIC_USAGE_STATISTIKEN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model.KafkaUsageRecord;


/**
 * Klasse zum Versenden von Kafka-Nachrichten.
 */
@Component
public class KafkaSender {

    private static Logger LOG = LoggerFactory.getLogger( KafkaSender.class );


    /**
     * Bean zum Versenden von Kafka-Nachrichten; sowohl der Schlüssel (Kürzel)
     * als auch die eigentlich Nachricht sind vom Typ {@code String}.
     */
    private KafkaTemplate<String, String> _kafkaTemplate;

    /** Bean für Umwandlung von Java-Objekt nach JSON-String. */
    private ObjectMapper _objectMapper;


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KafkaSender(KafkaTemplate<String, String> template,
                       ObjectMapper objecMapper) {

        _kafkaTemplate = template;
        _objectMapper  = objecMapper;
    }


    /**
     * Usage-Record für Aufruf einer Kurz-URL über Kafka verschicken.
     *
     * @param record Usage-Record, der über Kafka verschickt werden soll.
     */
    public void sendeUsageRecord(KafkaUsageRecord record) {

        try {

            String json = _objectMapper.writeValueAsString( record );
            _kafkaTemplate.send( TOPIC_USAGE_STATISTIKEN, json );
        }
        catch (Exception ex) {

            LOG.error( "Fehler beim Versenden eines Usage-Records über Kafka: " + ex.getMessage() );
        }
    }
}
