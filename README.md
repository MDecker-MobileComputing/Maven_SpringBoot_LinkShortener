# URL Shortener with Microservices #

<br>

This repository contains the source code for a [URL Shortener Service](https://en.wikipedia.org/wiki/URL_shortening)
implemented by several Java microservices (Maven and Spring Boot) with an embedded [H2 database](https://www.h2database.com).

<br>

A similar application with Nodejs (but still Microservices and Kafka) can be found [here](https://github.com/MDecker-MobileComputing/Nodejs_Express_LinkShortenerMitKafka).

<br>

----

## Overview ##

<br>

Before starting the microservices the Kafka server must be started using the `docker-compose.yml`
in [this folder](./Kafka/).
This file will also start a container with a simple load balancer for the two instances
of microservice 2 (resolver).

<br>

**[Microservice 1 (Short Link Definition)](Microservice1_Definition/):**
* [The only Instance: `localhost:8080`](http://localhost:8080)
* DAO: `JdbcTemplate`

<br>

**[Microservice 2: (Link Resolver)](Microservice2_Resolver/):**
* Instances:
    * [Instance 1: `localhost:8000`](http://localhost:8000)
    * [Instance 2: `localhost:8010`](http://localhost:8010)
    * [Load Balancer (nginx): `localhost:8123`](http://localhost:8123)
* DAO: `JdbcTemplate` and `NamedParameterJdbcTemplate`

<br>

**[Microservice 3: (Usage Statistics)](Microservice3_LinkStatistics/):**
* [The only Instance: `localhost:8050`](http://localhost:8050)
* DAO: `EntityManager` of *Jakarta Persistence API (JPA)*

<br>

DAO: **D**atabase **A**ccess **O**bject

<br>

----

## License ##

<br>

See the [LICENSE file](LICENSE.md) for license rights and limitations (BSD 3-Clause License).

<br>
