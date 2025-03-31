FROM openjdk:17-jdk-slim

# Делаем папку внутри контейнера для нашего приложения
WORKDIR /app

# Копируем JAR-файл в контейнер.  Убедитесь, что имя JAR файла соответствует вашему.
COPY *.jar apps.jar


EXPOSE 8081

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "apps.jar"]