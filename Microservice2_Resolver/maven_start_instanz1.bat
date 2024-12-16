@echo.

@del logdatei_instanz1.log 2> nul

mvnw clean spring-boot:run -Dspring-boot.run.profiles=instanz1
