package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.kafka;

import static org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Klasse mit Topics, die von Microservice 3 für Kommunikation mit anderen
 * Microservices über Kafka benötigt werden. Die Topics werden bei Bedarf
 * automatisch erzeugt.
 */
@Configuration
public class KafkaTopics {

    /**
     * Name von Kafka-Topic, an die Nachrichten über Aufrufe von URL-Kürzeln
     * von Microservice 2 verschickt werden.
     */
    public static final String TOPIC_USAGE_STATISTIKEN = "usage_statistiken";

    /**
     * Name von Kafka-Topic, an die Nachrichten mit dem User-Agent-String
     * geschrieben werden.
     */
    public static final String TOPIC_USER_AGENT_STRING = "browser_string";

    /**
     * Die Default-Lebensdauer einer Nachricht auf einem Kafka-Topic ist 7 Tage.
     * Man kann beim Anlegen eines Topics eine andere Lebensdauer angeben.
     * Wenn dabei der Wert "-1" angegeben wird, dann ist die Lebensdauer unbegrenzt
     * (unlimited retention period).
     */
    private static final String LEBENSDAUER_UNBEGRENZT = "-1";


    /**
     * Erzeugt bei Bedarf Topic für Usage-Statistiken.
     *
     * @return Topic für Usage-Statistiken mit unbegrenzter Lebensdauer,
     *         2 Partitionen und 1 Replica.
     */
    @Bean
    public NewTopic topicUsageStatistiken() {

        return TopicBuilder.name(TOPIC_USAGE_STATISTIKEN)
                           .partitions(2)
                           .replicas(1)
                           .config(RETENTION_MS_CONFIG, LEBENSDAUER_UNBEGRENZT)
                           .build();
    }


    /**
     * Erzeugt bei Bedarf Topic für User-Agent-String (Browser-Kennung), auf das 
     * mit Kafka-Stream geschrieben wird.
     *
     * @return Topic mit unbegrenzter Lebensdauer, 2 Partitionen und 1 Replica.
     */
    @Bean
    public NewTopic topicUserAgentString() {

        return TopicBuilder.name(TOPIC_USER_AGENT_STRING)
                           .partitions(2)
                           .replicas(1)
                           .config(RETENTION_MS_CONFIG, LEBENSDAUER_UNBEGRENZT)
                           .build();
    }

}
