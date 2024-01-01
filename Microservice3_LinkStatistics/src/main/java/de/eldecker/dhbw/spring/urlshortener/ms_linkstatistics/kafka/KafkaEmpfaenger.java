package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka;

import static de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.KafkaTopics.TOPIC_USAGE_STATISTIKEN;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db.Datenbank;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.KafkaLinkStat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Bean-Klasse um Kafka-Nachrichten mit Link-Statistiken zu empfangen.
 */
@Component
public class KafkaEmpfaenger {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEmpfaenger.class);

    /** Bean für Deserialisierung von JSON nach Java-Objekt.  */
    private ObjectMapper _objectMapper;

    /** Bean mit Datenbank-Code, um empfangene Nachrichten zu persistieren. */
    private Datenbank _datenbank;


    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KafkaEmpfaenger(Datenbank datenbank,
                           ObjectMapper objectMapper) {

        _datenbank    = datenbank;
        _objectMapper = objectMapper;
    }


    /**
     * Diese Methode wird von Spring aufgerufen, wenn eine Nachricht auf dem
     * Kafka-Topic {@link KafkaTopics#TOPIC_USAGE_STATISTIKEN} empfangen wurde.
     *
     * @param jsonString Payload der Nachricht
     */
    @KafkaListener(id = "ustat-listener", topics = TOPIC_USAGE_STATISTIKEN)
    public void onNachrichtEmpfangen(String jsonString) {

        try {

            KafkaLinkStat kafkaLinkStat = _objectMapper.readValue(jsonString, KafkaLinkStat.class);

            _datenbank.speichereLinkZugriff( kafkaLinkStat.kuerzel(),
                                             kafkaLinkStat.erfolgreich(),
                                             kafkaLinkStat.datumUndZeit() );

            LOG.info("Von Kafka empfangener Link-Zugriff-Record in DB gespeichert: {}", kafkaLinkStat);
        }
        catch (JsonProcessingException ex) {

            LOG.error("Fehler beim Deserialisieren einer Kafka-Nachricht mit LinkStat", ex);
        }

    }

}
