# # Первый этап: создание образа Tomcat
# FROM tomcat:9.0-jre8 AS tomcat_image

# # Создайте каталог для вашего приложения
# # RUN mkdir /usr/local/tomcat/webapps/myapp

# # Копируйте WAR файл вашего приложения в контейнер
# # COPY target/my-app.war /usr/local/tomcat/webapps/myapp/

# # Второй этап: создание образа с LpSolve
# FROM everpeace/lpsolve-java:latest AS lpsolve_image

# # Установите переменные окружения, если требуется
# # ENV VAR_NAME=value

# # Третий этап: объединение образов Tomcat и LpSolve
# FROM tomcat AS tomcat_lpsolve

# # Копируйте файлы из образа Tomcat
# # COPY --from=tomcat /usr/local/tomcat/webapps/myapp /usr/local/tomcat/webapps/myapp

# # Копируйте файлы из образа LpSolve
# COPY --from=lpsolve /opt/lp_solve_5.5_java/lib/lpsolve55j.jar /usr/local/tomcat/lib/lpsolve55j.jar

# # Установите переменные окружения, если требуется
# # ENV VAR_NAME=value

# # Запуск Tomcat
# CMD ["catalina.sh", "run"]
# Используйте официальный образ Ubuntu для установки lp-solve
FROM ubuntu:20.04 AS builder

# Обновите список пакетов и установите lp-solve
RUN apt-get update && \
    apt-get install -y lp-solve && \
    rm -rf /var/lib/apt/lists/*

# Соберите JAR-файл lp-solve
RUN cd /usr/share/doc/lp-solve*/java && \
    jar cf lp-solve.jar *.class

# Используйте официальный образ Tomcat
FROM tomcat:9.0-jre8

# Копируйте JAR-файл lp-solve в lib каталог Tomcat
COPY --from=builder /usr/share/doc/lp-solve*/java/lp-solve.jar /usr/local/tomcat/lib/

# Создайте каталог для вашего приложения
# RUN mkdir -p /usr/local/tomcat/webapps/myapp

# Копируйте WAR файл вашего приложения в контейнер
# COPY target/my-app.war /usr/local/tomcat/webapps/myapp/

# Запуск Tomcat
CMD ["catalina.sh", "run"]
