# Microservice 2: URL-Resolver #

<br>

Dieser Unterordner enthält den Quellcode für eine Spring-Boot-Anwendung als
Maven-Projekt, das den Microservice zum Auflösen der Kurz-URLs enthält.
Von diesem Microservice sollten mehrere Instanzen gleichzeitig laufen, weil
es sich um die am häufigsten von Endnutzern verwendete Funktion der
Anwendung handelt.
Jede Microservice-Instanz hat eine eigene Datenbank, die sie anhand der vom
Microservice 1 erhaltenen Kafka-Nachrichten befüllt.

<br>

Die verschiedenen Microservice-Instanzen werden über Spring-Profile auseinandergesteuert:

| Profil-Name | Log-Datei                | Datenbankdatei                  | Port-Nummer                   |
| ----------- | ------------------------ | ------------------------------- | ----------------------------- |
| `instanz1`  | `logdatei_instanz1.log`  | `h2_datenbank_ms2_instanz1.mv`  | [8000](http://localhost:8000) |
| `instanz2`  | `logdatei_instanz2.log`  | `h2_datenbank_ms2_instanz2.mv`  | [8010](http://localhost:8010) |

<br>