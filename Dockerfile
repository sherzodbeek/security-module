FROM postgres:latest
ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD root123
ENV POSTGRES_DB service-db
COPY init.sql /docker-entrypoint-initdb.d/

# docker build -t service-db ./
# docker run -d --name service-db-container -p 5433:5432 service-db