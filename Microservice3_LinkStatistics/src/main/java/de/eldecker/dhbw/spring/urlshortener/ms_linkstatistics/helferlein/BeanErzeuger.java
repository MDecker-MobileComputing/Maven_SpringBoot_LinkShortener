package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein;


import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.serde.MeinSerde;

import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.apache.kafka.common.serialization.Serdes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;


/**
 * Die Klasse ist mit {@code Configuration} annotiert, weil sie mit {@code @Bean}
 * annotierte Methoden hat, die Objekte für <i>Dependency Injection</i> oder
 * Konfigurationen bereitstellen
 */
@Configuration
public class BeanErzeuger {

    /**
     * Liefert konfiguriertes ObjectMapper-Objekt zurück, welches für Object-nach-JSON (Serialisierung)
     * oder JSON-nach-Objekt (Deserialisierung) benötigt wird.
     *
     * @return Konfigurierter Object-Mapper
     */
    @Bean
    public ObjectMapper erzeugeObjectMapper() {

        return JsonMapper.builder()
                .disable(FAIL_ON_UNKNOWN_PROPERTIES) // Ignoriert unbekannte JSON-Felder beim Deserialisieren
                .disable(WRITE_DATES_AS_TIMESTAMPS)  // Schreibt Datum und Zeit im ISO-8601-Format
                .enable(INDENT_OUTPUT)               // Erzeugtes JSON mit Einrückungen, damit gut für Menschen lesbar
                .build();
    }


    /**
     * Konfiguration für "Kafka Stream", der Link-Stat auf User-Agent-String abbildet und
     * auf ein anderes Topic schreibt.
     *
     * @return Konfiguration für "Kafka Stream"
     */
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {

        Map<String, Object> props = new HashMap<>();

        props.put(APPLICATION_ID_CONFIG           , "linkstat-to-useragent-stream");
        props.put(BOOTSTRAP_SERVERS_CONFIG        , "localhost:9092");
        props.put(GROUP_ID_CONFIG                 , "die-streaming-gruppe"); // andere Gruppe als Receiver, damit beide parallel laufen können
        /*
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG  , MeinSerde.class.getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        */

        return new KafkaStreamsConfiguration(props);
    }

}
