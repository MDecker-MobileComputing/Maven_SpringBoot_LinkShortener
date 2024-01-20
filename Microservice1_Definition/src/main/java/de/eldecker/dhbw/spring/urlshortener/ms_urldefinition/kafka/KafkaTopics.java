package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.kafka;

import static org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Diese Klasse enthält die Namen der Kafka-Topics, die in diesem Microservice
 * verwendet werden. Außerdem sorgt sie dafür, dass dieses Topic bei Bedarf
 * erzeugt wird.
 */
@Configuration
public class KafkaTopics {

    /**
     * Name Kafka-Topic, an die Nachrichten über neu erzeugte URL-Definitionen
     * verschickt werden.
     */
    public static final String TOPIC_URL_DEFINITION = "url_definition";


    /**
     * Diese Methode liefert eine Bean, die das Kafka-Topic für URL-Definitionen bei Bedarf
     * erzeugt.
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
                           .config(RETENTION_MS_CONFIG, "-1") // -1=unbegrenzte Lebensdauer der Nachrichten
                           .build();
    }

}
