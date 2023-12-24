@echo.

del logdatei.log 2> nul

@REM Spring-Profil "entwicklung" aktivieren: H2-Konsole aktivieren
mvnw clean spring-boot:run -Dspring-boot.run.profiles=entwicklung
