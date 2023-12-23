
CREATE TABLE IF NOT EXISTS urls (

    id                  INT /* AUTO_INCREMENT */ PRIMARY KEY,
    url_original        VARCHAR(999) NOT NULL,
    url_kuerzel         VARCHAR(255) NOT NULL,
    beschreibung        TEXT,
    zeitpunkt_erzeugung TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ist_aktiv           BOOLEAN DEFAULT TRUE,
    passwort            VARCHAR(255),
    ist_autogeneriert   BOOLEAN DEFAULT FALSE
    /* zeitpunkt_abschaltung TIMESTAMP, */    
);