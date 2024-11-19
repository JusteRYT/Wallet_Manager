# Wallet Application

## Описание проекта

Wallet Application — это REST API приложение для управления балансом кошельков.  
Приложение предоставляет возможность пополнять баланс, снимать средства и получать текущий баланс кошелька.

### Основные технологии:
- **Java 17** + **Spring Boot 3**
- **PostgreSQL** с миграциями через Liquibase
- **Docker** и **Docker Compose** для контейнеризации
- Нагрузка 1000 RPS (конкурентная среда)
- Тестирование: JUnit 5, Mockito, Spring Boot Test

---

## Установка и запуск

### Требования
- Docker 24+
- Docker Compose 2.20+

### Шаги:
1. Клонируйте репозиторий:
   ```bash
   git clone <ссылка на репозиторий>
   cd wallet-application
   ```

2. Соберите JAR-файл приложения:
   ```bash
   ./mvnw clean package
   ```

3. Запустите приложение с помощью Docker Compose:
   ```bash
   docker-compose up --build
   ```

Приложение будет доступно по адресу: `http://localhost:8080`.

---

## API Эндпоинты

### 1. Пополнение/снятие средств
**POST** `/api/v1/wallets`

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

---

### 2. Получение баланса
**GET** `/api/v1/wallets/{walletId}`

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

## Тесты

Запуск тестов:
```bash
./mvnw test
```
