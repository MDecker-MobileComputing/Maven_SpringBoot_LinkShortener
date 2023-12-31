package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model;

import de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka.KafkaTopics;

import java.util.Date;


/**
 * Klasse für ein Kafka-Record, der die Daten eines Aufrufs eines URL-Kürzels
 * enthält. Nachrichten dieser Klassen werden an das Kafka-Topic
 * {@link KafkaTopics#TOPIC_USAGE_STATISTIKEN} geschickt.
 *
 * @param zeitpunkt Zeitpunkt des Aufrufs
 *
 * @param urlKuerzel URL-Kürzel, das aufgelöst werden sollte
 *
 * @param userAgentString User-Agent-String des Browsers
 *
 * @param konnteAufgeloestWerden {@code true} gdw. das URL-Kürzel aufgelöst
 */
public record KafkaUsageRecord( Date zeitpunkt,
                                String urlKuerzel,
                                String userAgentString,
                                boolean konnteAufgeloestWerden ) {
}
