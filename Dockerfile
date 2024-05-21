# Используйте официальный образ Tomcat с JRE 1.8
FROM tomcat:9-jre8

# Удалите старый JRE
RUN apt-get update && apt-get remove openjdk-\*

# Скачайте и распакуйте желаемую версию JRE
ADD https://github.com/user/repo/raw/branch/path/to/jre-8u111-linux-i586.tar.gz /tmp/
RUN tar xzf /tmp/jre-8u111-linux-i586.tar.gz -C /opt/

# Переименуйте папку JRE
RUN mv /opt/jre1.8.0_111 /opt/jre

# Установите переменные окружения
ENV JAVA_HOME=/opt/jre
ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$PATH:$JAVA_HOME/bin

# Запустите Tomcat
CMD ["catalina.sh", "run"]





