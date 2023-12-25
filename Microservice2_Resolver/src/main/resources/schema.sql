
-- Damit die SQL-Statements in dieser Datei beim Start der Anwendung ausgeführt werden,
-- muss in der Datei application.properties die folgende Zeile stehen:
-- spring.sql.init.mode=always

CREATE TABLE IF NOT EXISTS kurzlinks (

    id                      INT /* AUTO_INCREMENT */ PRIMARY KEY,
    -- kein AUTO_INCREMENT, weil wir die IDs aus Kafka-Nachrichten bekommen übernehmen
    url_original            VARCHAR(999) NOT NULL,
    url_kuerzel             VARCHAR(255) NOT NULL,
    beschreibung            TEXT,
    zeitpunkt_erzeugung     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    zeitpunkt_aktulisierung TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ist_aktiv               BOOLEAN DEFAULT TRUE
    -- passwort braucht dieser Microservice nicht!
);
