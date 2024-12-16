# Dockerfile to build Load Balancer #

<br>

This folder contains a [Dockerfile](./Dockerfile), that builds an image for
a load balancer in front of the two instances of microservice 2 (resolver).
As load balancer [nginx](https://nginx.org/en/) is used, therefore the base image is
`nginx:1.25.3-alpine3.18`.

<br>

Command to build an image according to this `Dockerfile`:

```
docker build --tag loadbalancer-linkshortener:1.0.2 .
```

<br>

Public repo with this image: https://hub.docker.com/r/mide76/loadbalancer-linkshortener

<br>