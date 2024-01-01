package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.serde;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein.BeanErzeuger;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.KafkaBrowserUserAgentString;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Deserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrowserKennungDeserialisierer
       implements Deserializer<KafkaBrowserUserAgentString> {

    private static Logger LOG = LoggerFactory.getLogger(BrowserKennungDeserialisierer.class);

    /**
     * Es wird nicht die {@code ObjectMapper}-Bean von der Klasse {@link BeanErzeuger}
     * verwendet, weil laut <i>GitHub Copilot</i>:
     * "... in the context of Kafka Serdes, it's not straightforward because Serdes
     * are typically instantiated by the Kafka client itself, not by the Spring container."
     */
    private final ObjectMapper _objectMapper;


    /**
     * Konstruktor, erzeugt {@code ObjectMapper}-Objekt und konfiguriert es.
     */
    public BrowserKennungDeserialisierer() {

        _objectMapper = new ObjectMapper();
        _objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * Methode deserialisiert von Input-Topic gelesene Daten in ein Objekt
     * vom Typ {@link KafkaBrowserUserAgentString}.
     *
     * @param topic Name des Topics, aus dem gelesen wird (wird nicht ausgewertet)
     * @param data Daten, die deserialisiert werden sollen
     * @return Deserialisiertes Objekt vom Typ {@link KafkaBrowserUserAgentString}
     *         oder {@code null}, wenn {@code data} {@code null} oder leer ist
     */
    @Override
    public KafkaBrowserUserAgentString deserialize(String topic, byte[] data) {

        if (data == null || data.length == 0) {

            LOG.warn("Leeres byte-Array erhalten, kann nicht deserialisiert werden: " + data);
            return null;
        }

        try {

            KafkaBrowserUserAgentString result = _objectMapper.readValue(data, KafkaBrowserUserAgentString.class);

            LOG.info("Deserialisiertes KafkaBrowserUserAgentString-Objekt: {}", result);

            return result;
        }
        catch (IOException ex) {

            LOG.error("Fehler beim Deserialisieren von Input-Topic: " + data);
            return null;
        }
    }

}
