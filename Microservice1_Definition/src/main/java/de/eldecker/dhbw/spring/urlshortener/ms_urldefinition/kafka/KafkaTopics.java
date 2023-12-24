package de.eldecker.dhbw.spring.urlshortener.ms_urldefinition.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Diese Klasse enthält die Namen der Kafka-Topics, die in diesem Microservice
 * verwendet werden. Außerdem sorgt sie dafür, dass dieses Topic bei Bedarf
 * erzeugt wird.
 * <br><br>
 *
 * Wenn man eine Terminalverbindung zum Docker-Container mit Kafka aufbaut,
 * dann kann man den folgenden Befehl eingeben, um alle Topics angezeigt zu
 * bekommen:
 * <pre>
 *   kafka-topics --list --bootstrap-server localhost:9092
 * </pre><br>
 *
 * Mit dem folgenden Befehl kann man sich die Details zum Topic {@code url_definition}
 * anzeigen lassen:
 * <pre>
 *  kafka-configs --bootstrap-server localhost:9092 --entity-type topics --entity-name url_definition --describe
 * </pre><br>
 *
 * Man kann sich auch alle Nachrichten auf dem Topic anzeigen lassen:
 * <pre>
 * kafka-console-consumer --bootstrap-server localhost:9092 --topic url_definition --from-beginning
 * </pre>
 * Wenn man bei diesem Befehl das {@code --from-beginning} weglässt, dann werden nur Nachrichten angezeigt,
 * die nach dem Start des Befehls auf dem Topic eingegangen sind.
 * <br><br>
 *
 * Es gibt auch einen Befehl, mit dem man das Topic löschen kann (Achtung: dabei gehen alle Nachrichten verloren!):
 * <pre>
 * kafka-topics --delete --bootstrap-server localhost:9092 --topic url_definition
 * </pre><br>
 */
@Configuration
public class KafkaTopics {

    /**
     * Name Kafka-Topic, an die Nachrichten über neu erzeugte URL-Definitionen
     * verschickt werden.
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
