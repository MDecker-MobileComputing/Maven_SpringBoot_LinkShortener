package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.serde;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein.BeanErzeuger;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.KafkaBrowserUserAgentString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Serialisierer für Kafka, um Objekte vom Typ {@link KafkaBrowserUserAgentString}
 * nach {@code byte}-Array zu serialisieren; wird für Kafka-Streams benötigt.
 */
public class BrowserKennungSerializer implements Serializer<KafkaBrowserUserAgentString> {

    private static Logger LOG = LoggerFactory.getLogger(BrowserKennungSerializer.class);

    /**
     * Es wird nicht die {@code ObjectMapper}-Bean von der Klasse {@link BeanErzeuger}
     * verwendet, weil laut <i>GitHub Copilot</i>:
     * "... in the context of Kafka Serdes, it's not straightforward because Serdes
     * are typically instantiated by the Kafka client itself, not by the Spring container."
     */
    private final ObjectMapper _objectMapper = new ObjectMapper();


    /**
     * Serialisierungsziel ist ein {@code byte}-Array. Auch wenn Kafka String-Nachrichten
     * verarbeiten kann, arbeitet es intern mit {@code byte}-Arrays.
     *
     * @param topic Name des Topics, für das serialisiert wird (wird nicht ausgewertet)
     *
     * @param object Objekt, das serialisiert werden soll
     */
    @Override
    public byte[] serialize(String topic, KafkaBrowserUserAgentString objekt) {

        try {

            return _objectMapper.writeValueAsBytes(objekt);
        }
        catch (JsonProcessingException ex) {

            LOG.error("Fehler beim Serialisieren von KafkaBrowserUserAgentString-Objekt: {}",
                      objekt, ex);
            
            return new byte[0];
        }
    }

}
