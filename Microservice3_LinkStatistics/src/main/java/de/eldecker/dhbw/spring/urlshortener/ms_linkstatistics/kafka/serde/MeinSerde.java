package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.serde;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.KafkaBrowserUserAgentString;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;


/**
 * Eigener Serialisierer/Deserialisierer für Kafka, um Objekte vom Typ
 * {@link KafkaBrowserUserAgentString} zu serialisieren und zu deserialisieren.
 */
public class MeinSerde
       implements Serde<KafkaBrowserUserAgentString> {

    /** Object with matching pair of serializer and deserializer. */
    private final Serde<KafkaBrowserUserAgentString> _serde;


    /**
     * Konstruktor Serialisierer/Deserialisierer für Kafka für Objekte
     * vom Typ {@link KafkaBrowserUserAgentString}.
     */
    public MeinSerde() {

        _serde = Serdes.serdeFrom(
                    new BrowserKennungSerializer(),
                    new BrowserKennungDeserialisierer()
        );
    }


    /**
     * Getter für den Serialisierer.
     */
    @Override
    public Serializer<KafkaBrowserUserAgentString> serializer() {

        return _serde.serializer();
    }


    /**
     * Getter für den Deserialisierer.
     */
    @Override
    public Deserializer<KafkaBrowserUserAgentString> deserializer() {

        return _serde.deserializer();
    }


    /**
     * Konfiguriert den Serialisierer/Deserialisierer.
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

        _serde.configure(configs, isKey);
    }


    /**
     * Schließt den Serialisierer/Deserialisierer.
     */
    @Override
    public void close() {

        _serde.close();
    }

}
