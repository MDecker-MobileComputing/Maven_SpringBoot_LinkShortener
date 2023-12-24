
-- Damit die SQL-Statements in dieser Datei beim Start der Anwendung ausgeführt werden,
-- muss in der Datei application.properties die folgende Zeile stehen:
-- spring.sql.init.mode=always

CREATE TABLE IF NOT EXISTS urls (

    id                  INT AUTO_INCREMENT PRIMARY KEY,
    url_original        VARCHAR(999) NOT NULL,
    url_kuerzel         VARCHAR(255) NOT NULL, -- reines Kürzel, z.B. "ab3", also ohne Domain des Resolver-Dienstes
    beschreibung        TEXT,
    zeitpunkt_erzeugung TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    zeitpunkt_aenderung TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- letzte Änderung
    ist_aktiv           BOOLEAN DEFAULT TRUE,
    passwort            VARCHAR(255)
);

-- In der Spalte "passwort" wird das Passwort im Klartext gespeichert.
-- Das ist natürlich nicht sicher, aber für diese Beispielanwendung ausreichend.
-- In einer echten Anwendung sollte das Passwort gehasht und gesalzen werden.
