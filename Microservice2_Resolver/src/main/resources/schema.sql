
-- Damit die SQL-Statements in dieser Datei beim Start der Anwendung ausgeführt werden,
-- muss in der Datei application.properties die folgende Zeile stehen:
-- spring.sql.init.mode=always

CREATE TABLE IF NOT EXISTS kurzlinks (

    id                       INT /* AUTO_INCREMENT */ PRIMARY KEY,
    -- kein AUTO_INCREMENT, weil wir die IDs aus Kafka-Nachrichten bekommen übernehmen
    url_original             VARCHAR(999) NOT NULL,
    url_kuerzel              VARCHAR(255) NOT NULL,
    beschreibung             TEXT,
    zeitpunkt_erzeugung      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    zeitpunkt_aktualisierung TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ist_aktiv                BOOLEAN DEFAULT TRUE
    -- passwort braucht dieser Microservice nicht!
);


-- Index für die Spalte url_kuerzel, damit für die Query zum Auflösen eines Kürzels
-- die Datenbank nicht die ganze Tabelle durchsuchen muss (Vermeidung "Full Table Scan")
CREATE INDEX idx_kurzlinks_url_kuerzel ON kurzlinks(url_kuerzel);
