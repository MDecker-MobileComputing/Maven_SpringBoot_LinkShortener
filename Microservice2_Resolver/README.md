# Microservice 2: URL-Resolver #

<br>

Dieser Unterordner enthält den Quellcode für eine Spring-Boot-Anwendung als
Maven-Projekt, das den Microservice zum Auflösen der Kurz-URLs enthält.
Von diesem Microservice sollten mehrere Instanzen gleichzeitig laufen, weil
es sich um die am häufigsten von Endnutzern verwendete Funktion der
Anwendung handelt.
Jede Microservice-Instanz hat eine eigene Datenbank, die sie anhand der vom
Microservice 1 erhaltenen Nachrichten befüllt.

<br>