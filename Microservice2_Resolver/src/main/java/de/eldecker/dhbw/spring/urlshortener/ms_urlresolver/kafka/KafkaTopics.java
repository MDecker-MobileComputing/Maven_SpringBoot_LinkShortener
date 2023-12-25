package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Klasse mit Topics, die von Microservice 2 für Kommunikation mit anderen
 * Microservices über Kafka benötigt wird.
 */
@Configuration
public class KafkaTopics {

    /**
     * Name Kafka-Topic, an die Nachrichten über neu erzeugte URL-Definitionen
     * vom Microservice 1 verschickt werden.
     */
    public static final String TOPIC_URL_DEFINITION = "url_definition";


    /**
     * Diese Methode liefert eine Bean, die das Kafka-Topic für URL-Definitionen
     * bei Bedarf erzeugt.
     *
     * @return Topic {@link TOPIC_URL_DEFINITION} mit unbegrenzter Lebenszeit der Nachrichten
     *         (unlimited retention period), vier Partitionen (für Parallelität) und 1 "Replikat"
     *         (damit ein Kafka-Server ausreicht).
     */
    @Bean
    public NewTopic topicUrlDefinition() {

        return TopicBuilder.name(TOPIC_URL_DEFINITION)
                .partitions(4)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "-1") // -1=unbegrenzte Lebensdauer der Nachrichten
                .build();
    }

}
