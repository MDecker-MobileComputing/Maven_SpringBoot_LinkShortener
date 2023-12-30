# Microservice 2: URL Resolver #

<br>

This subfolder contains the source code for a Spring Boot application as a
Maven project, which contains the microservice for resolving the short URLs.
Several instances of this microservice should run simultaneously because
it is the function of the application most frequently used by end users.
application by end users.
Each microservice instance has its own database, which it can update using the
Kafka messages received from Microservice 1.

<br>

The various microservice instances are controlled separately via Spring profiles:

| Profile     | Log File                 | DB file in folder `db/`         | Port Number                   |
| ----------- | ------------------------ | ------------------------------- | ----------------------------- |
| `instanz1`  | `logdatei_instanz1.log`  | `h2_datenbank_ms2_instanz1.mv`  | [8000](http://localhost:8000) |
| `instanz2`  | `logdatei_instanz2.log`  | `h2_datenbank_ms2_instanz2.mv`  | [8010](http://localhost:8010) |

<br>