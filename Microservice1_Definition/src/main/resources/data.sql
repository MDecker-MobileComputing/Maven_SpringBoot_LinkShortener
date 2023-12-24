
/*
INSERT INTO urls (id, url_original, url_kuerzel, beschreibung, passwort, ist_autogeneriert)
            VALUES ( 1,
                    'https://cloud.google.com/blog/products/gcp/7-ways-we-harden-our-kvm-hypervisor-at-google-cloud-security-in-plaintext?hl=en',
                    'a',
                    'Beispieldatensatz',
                    'demo-123',
                    FALSE);
*/

/* MERGE statt INSERT, damit keine Fehlermeldung bei mehrfachem Ausführen der Datei auftritt. */

MERGE INTO urls (url_original, url_kuerzel, beschreibung, passwort)
    KEY(url_kuerzel) -- Anhand Werten in Spalte "url_kuerzel" wird entschieden, ob ein Datensatz neu angelegt oder aktualisiert wird.
    VALUES (
        'https://cloud.google.com/blog/products/gcp/7-ways-we-harden-our-kvm-hypervisor-at-google-cloud-security-in-plaintext?hl=en',
        'a',
        'Artikel von Google, in dem erwähnt wird, dass die Google Cloud Platform (GCP) die Sicherheit des Hypervisors "KVM" verbessert hat.',
        'demo-123'
    );


MERGE INTO urls (url_original, url_kuerzel, beschreibung, passwort)
    KEY(url_kuerzel)
    VALUES (
        'https://subscription.packtpub.com/book/cloud-and-networking/9781789137231/17/ch17lvl1sec92/overview-of-docker-hub-and-its-operations',
        'b',
        'Docker Hub: pull and push images',
        'demo-abc'
    );
