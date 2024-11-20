# 💰 Wallet Management API

## 📜 Описание
Приложение для управления кошельками с использованием REST API, поддерживающее следующие функции:
- 📈 Пополнение и списание средств.
- 💼 Получение текущего баланса кошелька.

## 🛠️ Технологический стек
- ☕ **Java**: версии 8-17.
- 🌱 **Spring Boot**: 3.x.
- 🐘 **PostgreSQL**: для хранения данных.
- 🔄 **Liquibase**: управление миграциями базы данных.
- 🐳 **Docker**: для контейнеризации приложения и базы данных.
- 🧪 **Тестирование**: JUnit 5, Mockito, Spring Boot Test


## 📦 Запуск приложения
1. 🔧 Убедитесь, что Docker установлен и работает.
2. 🐳 Выполните команду для запуска приложения и базы данных:

   ```bash
   docker-compose up -d
   ```

4. 🔍 Проверьте состояние контейнеров:

   ```bash
   docker ps
   ```

Приложение будет доступно по адресу: `http://localhost:8080`.


## 📋 API Эндпоинты

- **POST** `/api/v1/wallets` - 🔄 Изменение баланса кошелька.

#### Пример запроса:

```json
{
  "walletId": "ab0af497-c5a0-45cb-9262-659048e8e6d0",
  "operationType": "DEPOSIT",
  "amount": 100.0
}
```

#### Ответы:
- **200 OK**: Успешное выполнение операции.
- **400 Bad Request**: Ошибки валидации (например, некорректный JSON).
- **404 Not Found**: Кошелек не найден.
- **409 Conflict**: Недостаточно средств для снятия.


- **GET** `/api/v1/wallets/{walletId}` - 📊 Получение баланса кошелька.

#### Пример запроса:

```bash
curl -X GET http://localhost:8080/api/v1/wallets/ab0af497-c5a0-45cb-9262-659048e8e6d0
```

#### Пример ответа:

```json
{
  "walletId": "ab0af497-c5a0-45cb-9262-659048e8e6d0",
  "balance": 150.0
}
```

## Файлы миграции Liquibase

Миграции описаны в папке `src/main/resources/db/changelog`:
- `db.changelog-master.yaml`: основной файл миграций.
- `db.changelog-001-init.yaml`: структура таблиц (`wallet`, `transaction`).

---

## 🧪 Тестирование
1. 📂 Убедитесь, что приложение запущено.
2. 🚦 Запустите JUnit тесты для проверки функционала.

## 🗂️ Структура проекта
- `src/main/java` - исходный код приложения.
- `src/test/java` - модульные тесты.
- `src/main/resources/db/changelog` - файлы миграций Liquibase.

## ⚙️ Конфигурация
- Конфигурация приложения в `application.yml`:

  ```yaml
  spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/wallet_db
    username: wallet_user
    password: wallet_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 30
      idle-timeout: 30000
      max-lifetime: 60000
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect
  liquibase:
    enabled: true
    change-log: classpath:/db/changelog/db.changelog-master.yaml
 server:
  tomcat:
    connection-timeout: 20000
    threads:
      max: 500
    max-connections: 10000
  ```

  ## 🛡️ Обработка ошибок
- **400**: Неверный запрос (например, невалидный JSON).
- **404**: Кошелек не найден.
- **500**: Внутренняя ошибка сервера.

## 📂 Локальная разработка
1. 🖥️ Клонируйте репозиторий:

   ```bash
   git clone https://github.com/JusteRYT/Wallet_Manager.git
   ```

3. 🏗️ Соберите проект:

   ```bash
   ./mvnw clean package
   ```

5. 🛠️ Запустите приложение локально:

   ```bash
   java -jar target/wallet-app.jar
   ```

## 📝 Лицензия
Данный проект находится под лицензией MIT. 📜
