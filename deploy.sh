#!/bin/bash

# Установка Maven
sudo apt-get update && sudo apt-get install maven
wait

# Установка Docker CE, CLI и containerd.io
sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io
wait

# Загрузка Docker Compose
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh
wait

# Установка Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
wait


# Настройка Maven для работы с локальным репозиторием
echo "Configuring Maven local repository..."
mkdir -p ~/.m2/Tomcat
cp settings.xml ~/.m2/settings.xml

# Добавление библиотек в локальный репозиторий
echo "Adding libraries to local Tomcat..."
cd /cr4nkle/Tomcat
for file in../lib/*.jar; do
    groupId=$(echo "$file" | sed -e 's/.*\/\(.*\)\/.*\.jar/\1/')
    artifactId=$(basename "$file".jar)
    version=$(echo "$file" | grep -oP '(?<=v)[^/]+')
    mvn install:install-file -Dfile="$file" -DgroupId="$groupId" -DartifactId="$artifactId" -Dversion="$version" -Dpackaging=jar
done

# Сборка проекта
echo "Building project..."
cd /cr4nkle/Tomcat
mvn clean:clean compile:compile war:war

# Запуск Docker Compose
echo "Starting Docker Compose..."
docker-compose up
