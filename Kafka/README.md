# Kafka-Server mit Docker Compose #

<br>

Dieses Verzeichnis enthält die Datei [docker-compose.yml](./docker-compose.yml),
mit der ein lokaler Kafka-Server in einem Docker-Container gestartet werden kann.
Auch ein einzelner Kafka-Server benötigt eine laufende ZooKeeper-Instanz, welche
auch in dieser Datei definiert ist.

<br>

Befehl zum Starten:
```
docker-compose up
```

<br>

Befehl zum Pausieren und Neustarten:
```
docker-compose stop
docker-compose start
```

<br>

Befehl zum Löschen (in Kafka-Container gespeicherte Nachrichten mit URL-Definitionen gehen verloren!):
```
docker-compose down
```

<br>

## Kafka-Befehle in Container ##

Wenn man eine Terminalverbindung zum Docker-Container mit Kafka aufbaut,
dann kann man den folgenden Befehl eingeben, um alle Topics angezeigt zu
bekommen:
```
kafka-topics --list --bootstrap-server localhost:9092
```

<br>

Mit dem folgenden Befehl kann man sich die Details zum Topic `url_definition` anzeigen lassen:
```
kafka-configs --bootstrap-server localhost:9092 --entity-type topics --entity-name url_definition --describe
```

<br>

Man kann sich auch alle Nachrichten auf dem Topic anzeigen lassen:
```
kafka-console-consumer --bootstrap-server localhost:9092 --topic url_definition --from-beginning
```
Wenn man bei diesem Befehl das Argument `--from-beginning` weglässt, dann werden nur aktuelle Nachrichten angezeigt, also Nachrichten, die nach dem Start des Befehls auf dem Topic eingegangen sind.

<br>

Es gibt auch einen Befehl, mit dem man das Topic löschen kann (Achtung: dabei gehen alle Nachrichten verloren!):
```
kafka-topics --delete --bootstrap-server localhost:9092 --topic url_definition
```

<br>