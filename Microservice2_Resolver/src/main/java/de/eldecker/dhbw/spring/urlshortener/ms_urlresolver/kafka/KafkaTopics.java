package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Klasse mit Topics, die von Microservice 2 für Kommunikation mit anderen
 * Microservices über Kafka benötigt werden. Die Topics werden bei Bedarf
 * automatisch erzeugt.
 */
@Configuration
public class KafkaTopics {

    /**
     * Name Kafka-Topic, an die Nachrichten über neu erzeugte URL-Definitionen
     * vom Microservice 1 verschickt werden.
     */
    public static final String TOPIC_URL_DEFINITION = "url_definition";

    /**
     * Name Kafka-Topic, an die Nachrichten über Aufrufe von URL-Kürzeln
     * von Microservice 2 verschickt werden.
     */
    public static final String TOPIC_USAGE_STATISTIKEN = "usage_statistiken";

    /**
     * Die Default-Lebensdauer einer Nachricht auf einem Kafka-Topic ist 7 Tage.
     * Man kann beim Anlegen eines Topics eine andere Lebensdauer angeben.
     * Wenn dabei der Wert "-1" angegeben wird, dann ist die Lebensdauer unbegrenzt
     * (unlimited retention period).
     */
    private static final String LEBENSDAUER_UNBEGRENZT = "-1";


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
                .config(TopicConfig.RETENTION_MS_CONFIG, LEBENSDAUER_UNBEGRENZT)
                .build();
    }

    @Bean
    public NewTopic topicUsageStatistiken() {

        return TopicBuilder.name(TOPIC_USAGE_STATISTIKEN)
                .partitions(4)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, LEBENSDAUER_UNBEGRENZT)
                .build();
    }

}
