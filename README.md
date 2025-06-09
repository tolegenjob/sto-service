# СТО-Сервис

**СТО-Сервис** — сервис для управления заявками на ремонт автомобилей. Поддерживает смену статусов заявок, хранение истории изменений, разграничение доступа по ролям, логирование, интеграцию с Kafka и mock-уведомления.

## 📦 Функциональность

- Создание и обновление заявок на ремонт
- Смена статусов заявок (NEW → ACCEPTED → IN_PROGRESS → REPAIRING → COMPLETED)
- История изменения статусов (`StatusHistory`)
- Поддержка ролей: `CLIENT`, `MECHANIC`, `MANAGER`, `ADMIN`
- Ограничения по доступу к действиям в зависимости от роли
- Mock-уведомление клиента о завершении заявки
- Kafka-логирование событий
- REST API
- Docker Compose окружение

## 🚀 Стек технологий

- Java 21+
- Spring Boot 3.4.2
- Spring Security
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Docker
- Lombok

## 🛠️ Установка и запуск

### Клонируйте репозиторий

```bash
git clone https://github.com/tolegenjob/sto-service.git
cd sto-service
```

### Настройте переменные окружения


```env
DB_NAME=your_db_name
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_token
JWT_EXPIRATION_MS=your_jwt_expiration_ms
```

### Сборка и Запуск через Docker Compose

```bash
./gradlew clean build -x test
docker-compose up --build
```

Приложение будет доступно на: `http://localhost:8080`

## 📌 API Endpoints

- API документация доступна по [Swagger](http://localhost:8080/swagger-ui.html)


## 🔐 Роли и доступ

| Роль     | Права доступа                                              |
|----------|------------------------------------------------------------|
| `ADMIN`  | Полный доступ ко всем сущностям (кроме создания задачи и транспорта)|
| `MANAGER`| Может переводить заявки в `ACCEPTED` и `IN_PROGRESS`, просматривать и удалять их       |
| `MECHANIC`| Может переводить заявки в `REPAIRING` и `COMPLETED`, просматривать заявки и транспорты, назначенные на него       |
| `CLIENT` | Может создавать и просматривать свои заявки и транспорты   |

## 📒 Логика смены статусов

- При смене статуса генерируется событие и сохраняется история (`StatusHistory`)
- Если статус — `IN_PROGRESS`, указывается механик
- Если статус — `COMPLETED`, мокируется уведомление клиента по номеру телефона
- Все смены статусов происходят через Kafka

## 🐳 Docker Compose

Сервис использует контейнеры:
- `sto-service` — основной микросервис
- `postgres` — база данных
- `kafka` — брокер событий
- `zookeeper` - управление брокером

## 👨‍💻 Автор

- [@tolegenjob](https://github.com/tolegenjob)
