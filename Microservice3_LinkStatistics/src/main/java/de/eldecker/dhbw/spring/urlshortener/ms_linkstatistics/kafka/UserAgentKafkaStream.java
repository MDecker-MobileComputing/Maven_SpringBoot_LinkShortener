package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka;

import static nl.basjes.parse.useragent.UserAgent.AGENT_NAME;
import static nl.basjes.parse.useragent.UserAgent.OPERATING_SYSTEM_NAME;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.helferlein.BeanErzeuger;
import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.serde.MeinSerde;

import static de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.KafkaTopics.TOPIC_USER_AGENT_STRING;
import static de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.KafkaTopics.TOPIC_USAGE_STATISTIKEN;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model.KafkaBrowserUserAgentString;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.stereotype.Component;


/**
 * Kafka-Stream, der den User-Agent-String (Browser-Kennung) aus
 * {@link KafkaBrowserUserAgentString}-Objekt extrahiert und in
 * ein anderes Topic schreibt.
 */
@Component
public class UserAgentKafkaStream {

    private static Logger LOG = LoggerFactory.getLogger( UserAgentKafkaStream.class );

    /** Stream, der User-Agent-String aus KafkaBrowserUserAgentString-Objekt extrahiert */
    private KStream<String, KafkaBrowserUserAgentString> _kstream;

    /** Kafka-Streams-Objekt, das den Stream ausführt */
    private KafkaStreams _kafkaStreams;

    
    /**
     * Definiert die Topologie des Kafka-Streams:
     * <pre>
     * TOPIC_USAGE_STATISTIKEN → Mapping-Operation → TOPIC_USER_AGENT_STRING
     * </pre>
     * <br>
     * User-Agent-String (Browser-Kennung) wird mit "Yauaa" ausgewertet, siehe
     * <a href="https://yauaa.basjes.nl" target="_blank">hier</a>.
     *
     * @param streamConfig Konfiguration für den Kafka-Stream, wird erzeugt von 
     *                     Methode {@link BeanErzeuger#erzeugeKStreamsConfig()}
     * 
     * @param userAgentAnalyzer Bean für Auswertung Browser-Kennung, Objekt von 
     *                          Klasse aus Bibliothek "Yauaa"
     */
    @Autowired
    public UserAgentKafkaStream(KafkaStreamsConfiguration streamConfig, UserAgentAnalyzer userAgentAnalyzer) {

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Input- und Output-Serdes für den Stream
        Consumed<String, KafkaBrowserUserAgentString> inputSerde  = Consumed.with( Serdes.String(), new MeinSerde() );
        Produced<String, String>                      outputSerde = Produced.with( Serdes.String(), Serdes.String() );

        _kstream = streamsBuilder.stream(TOPIC_USAGE_STATISTIKEN, inputSerde); // Input-Topic

        // stream mapping: extract user agent string from KafkaBrowserUserAgentString object
        _kstream.mapValues( (key, wert) -> {

                String userAgentString = wert.userAgentString();                
                LOG.info("User-Agent-String im Stream erhalten: {}", userAgentString);
                
                UserAgent agent = userAgentAnalyzer.parse(userAgentString);
                String betriebsSystem = agent.getValue(OPERATING_SYSTEM_NAME);
                String browserName    = agent.getValue(AGENT_NAME);
                
                String browserAufBetriebsSystem = String.format("%s %s", betriebsSystem, browserName);
                // Beispielwerte: "Windows NT Opera", "Windows NT Firefox", "Windows NT Chrome", "Windows NT Edge"
                                
                LOG.info("User-Agent-String im Stream wird abgebildet auf: {}", browserAufBetriebsSystem);
                
                return browserAufBetriebsSystem;
            })
        .to(TOPIC_USER_AGENT_STRING, outputSerde); // Output-Topic

        _kafkaStreams = new KafkaStreams( streamsBuilder.build(),
                                          streamConfig.asProperties() );
    }


    /**
     * Startet den Kafka-Stream, sobald die Applikation hochgefahren ist.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void start() {

        _kafkaStreams.start();
        LOG.info("Kafka-Stream gestartet");
    }


    /**
     * Stoppt den Kafka-Stream, wenn die Applikation herunterfährt.
     */
    @EventListener(ContextClosedEvent.class)
    public void stop() {

        LOG.info("Kafka-Stream wird geschlossen.");
        _kafkaStreams.close();
    }
    
}