
-- Diese SQL-Datei wird mit folgender Annotation in der Klasse "LoeschenTest" referenziert:
-- @Sql(scripts = {"/loeschen.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

-- Tabelle vor nächster Unit-Test-Methode löschen
DELETE FROM urls;
