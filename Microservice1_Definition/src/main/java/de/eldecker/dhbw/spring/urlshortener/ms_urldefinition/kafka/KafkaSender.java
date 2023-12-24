package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.model.KafkaUrlDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * Diese Bean-Klasse dient dazu, die URL-Definitionen über Kafka
 * an andere Microservices zu verteilen (z.B. die Resolver-Instanzen).
 */
@Component
public class KafkaSender {

    private static Logger LOG = LoggerFactory.getLogger( KafkaSender.class );

    /** Name Kafka-Topic, an die dieser Erzeuger Nachrichten schickt. */
    public static final String TOPIC_URL_DEFINITION = "url_definition";

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
     * URL-Definition über Kafka an andere Microservices verteilen.
     *
     * @param urlDefinition URL-Definition, die über Kafka an andere Microservices
     *                      verteilt werden soll; das Kürzel ist der Key, so dass
     *                      verschiedene Nachrichten für ein Kürzel in der richtigen
     *                      Reihenfolge verarbeitet werden können.
     *
     * @return {@code true}, wenn die URL-Definition erfolgreich über Kafka verteilt
     *         werden konnte, sonst {@code false}.
     */
    public boolean sendeUrlDefinition(KafkaUrlDefinition urlDefinition) {

        try {

            final String jsonString = _objectMapper.writeValueAsString(urlDefinition);

            _kafkaTemplate.send(TOPIC_URL_DEFINITION, urlDefinition.kuerzel(), jsonString);

            LOG.debug("JSON-String via Kafka verschickt: " + jsonString);

            return true;
        }
        catch (JsonProcessingException ex) {

            LOG.error("Fehler beim Umwandeln von URL-Definition in JSON-String: " + ex.getMessage());
            return false;
        }
    }

}
