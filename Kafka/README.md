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