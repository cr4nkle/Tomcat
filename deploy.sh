#!/bin/bash

# Проверяем наличие необходимых инструментов
if! command -v mvn &> /dev/null; then
    echo "Maven could not be found. Installing..."
    sudo apt-get install maven
fi

if! command -v docker &> /dev/null; then
    echo "Docker could not be found. Installing..."
    sudo apt-get install docker-ce docker-ce-cli containerd.io
fi

if! command -v docker-compose &> /dev/null; then
    echo "Docker Compose could not be found. Installing..."
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
fi

# Настройка Maven для работы с локальным репозиторием
echo "Configuring Maven local repository..."
mkdir -p ~/.m2/Tomcat
cp settings.xml ~/.m2/settings.xml

# Добавление библиотек в локальный репозиторий
echo "Adding libraries to local Tomcat..."
cd /app/Tomcat
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
