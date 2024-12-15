# Этап 1: Сборка приложения
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Сборка образа для выполнения
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранный .jar файл из предыдущего этапа
COPY --from=build /app/target/sports-store-0.2.jar /app/sports-store-0.2.jar

# Копируем статическое изображение
COPY src/main/resources/images/header.jpg /app/images/header.jpg

# Открываем порт 8080
EXPOSE 8080

# Устанавливаем точку входа
ENTRYPOINT ["java", "-jar", "/app/sports-store-0.2.jar"]
