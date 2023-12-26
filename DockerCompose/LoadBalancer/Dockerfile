# Offizielles Base Image für Webserver "nginx", siehe https://hub.docker.com/_/nginx/
FROM nginx:1.25.3-alpine3.18

COPY nginx.conf /etc/nginx/nginx.conf

# Expore HTTP port, to be mapped to port 8123 on the host
EXPOSE 80
