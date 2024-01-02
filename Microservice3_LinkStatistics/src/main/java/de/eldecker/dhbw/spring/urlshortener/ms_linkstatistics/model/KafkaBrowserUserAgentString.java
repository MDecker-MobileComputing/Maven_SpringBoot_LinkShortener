package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.model;

import de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka.KafkaTopics;


/**
 * Record-Klasse für Kafka-Stream mit Input-Topic {@link KafkaTopics#TOPIC_USAGE_STATISTIKEN}.
 * Es wird nur das Feld mit dem User-Agent-String deserialisiert.
 * 
 * @param userAgentString User-Agent-String (Browserkennung), z.B. für Chrome-Browser unter Windows:
 *                        {@code Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36}
 */
public record KafkaBrowserUserAgentString(String userAgentString) {
}
