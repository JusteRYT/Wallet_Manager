# Используем базовый образ JDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app
# Копируем файл jar с приложением в контейнер
COPY target/wallet-0.0.1-SNAPSHOT.jar app.jar

# Порт, на котором будет работать приложение
EXPOSE 8080


LABEL authors="JusteRYT"

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]