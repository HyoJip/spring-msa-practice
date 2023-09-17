FROM mariadb
ENV MARIADB_ROOT_PASSWORD 0000
ENV MARIADB_DATABASE bbbicb
COPY ./docker-source/mysql /var/lib/mysql
EXPOSE 3306
CMD ["--user=root"]
