# REST API — кредитный сервис

## 1. Аутентификация и регистрация

### 1.1. Регистрация пользователя

**Предназначение запроса:**  
Регистрация нового пользователя в системе. После успешной регистрации пользователю назначается роль `USER`, а API возвращает JWT-токен.

**Запрос:**

```http
POST /user/register
Content-Type: application/json
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Content-Type` | `application/json` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**

```json
{
  "firstname": "Robert",
  "lastname": "Feraro",
  "email": "example@gmail.com",
  "password": "Qwerty123"
}
```

| Поле | Тип | Обязательное | Доп ограничения | Описание |
|---|---|---|---|---|
| `firstname` | String | Да | Нет | Имя пользователя |
| `lastname` | String | Да | Нет | Фамилия пользователя |
| `email` | String | Да | Format: local_part@domain.com | Email пользователя |
| `password` | String | Да | Size(min = 8, max = 25), Pattern(regexp = "^(?!.*\\p{IsCyrillic})(?!.*\\s).*$" | Пароль пользователя |

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

**Возможные ответы при ошибках:**

| HTTP | Причина |
|---|---|
| `400 Bad Request` | Отсутствует обязательное поле |
| `400 Bad Request` | Пустое или некорректное значение поля |
| `400 Bad Request` | Email уже используется |
| `400 Bad Request` | Некорректный пароль |

В проекте для бизнес-ошибок используются следующие форматы:

```json
{
  "code": "ERROR_CODE",
  "message": "Описание ошибки"
}
```

```json
{
  "error": {
    "code": "ERROR_CODE",
    "message": "Описание ошибки"
  }
}
```

---

### 1.2. Авторизация пользователя

**Предназначение запроса:**  
Аутентификация зарегистрированного пользователя и получение JWT-токена для выполнения защищённых запросов.

**Запрос:**

```http
POST /user/authenticate
Content-Type: application/json
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Content-Type` | `application/json` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**

```json
{
  "email": "example@gmail.com",
  "password": "Qwerty123"
}
```

Важно, при первом запуске приложения создаётся пользователь администратор, аутентификацию можно пройти со следующими данными:

```json
{
  "email": "ivanov@mail.ru",
  "password": "1234"
}
```

| Поле | Тип | Обязательное | Доп ограничения | Описание |
|---|---|---|---|---|
| `email` | String | Да | Нет | Email пользователя |
| `password` | String | Да | Нет | Пароль пользователя |

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Возможные ответы при ошибках:**

| HTTP | Причина |
|---|---|
| `400 Bad Request` | Отсутствует email |
| `400 Bad Request` | Отсутствует password |
| `400 Bad Request` | Пользователь не найден |

---

# 2. Тарифы

## 2.1. Получение списка тарифов

**Предназначение запроса:**  
Получение списка доступных кредитных тарифов.

**Запрос:**

```http
GET /loan-service/getTariffs
```

**Headers:**  
Не требуются.

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**  
Отсутствует.

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

```json
{
    "data": {
        "tariffs": [
            {
                "id": 1,
                "type": "CONSUMER",
                "interest_rate": "11.9%"
            },
            {
                "id": 2,
                "type": "MORTGAGE",
                "interest_ rate": "5.9% }
            ]
        }
    }
```

**Возможные ответы при ошибках:**

| HTTP | Причина |
|---|---|
| `408 Request Timeout` | Превышено время ожидания получения тарифов |
| `500 Internal Server Error` | Непредвиденная ошибка сервера |

---

## 2.2. Добавление тарифа

**Предназначение запроса:**  
Создание нового кредитного тарифа. Доступно только пользователю с ролью `ADMIN`.

**Запрос:**

```http
POST /loan-service/addTariff
Authorization: Bearer <JWT>
Content-Type: application/json
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Authorization` | `Bearer <JWT>` | Да |
| `Content-Type` | `application/json` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**

```json
{
  "type": "CREDIT",
  "interest_rate": "10%"
}
```

| Поле | Тип | Обязательное | Доп ограничения | Описание |
|---|---|---|---|---|
| `type` | String | Да | Нет | Тип тарифа |
| `interest_rate` | String | Да | Нет | Процентная ставка |

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

```text
1
```

В теле ответа возвращается идентификатор созданного тарифа.

**Возможные ответы при ошибках:**

| HTTP | Причина |
|---|---|
| `401 Unauthorized` | JWT отсутствует или недействителен |
| `403 Forbidden` | Пользователь не имеет роли `ADMIN` |
| `400 Bad Request` | Отсутствует обязательное поле |
| `400 Bad Request` | Обязательное поле содержит `null` или пустое значение |

---

## 2.3. Удаление тарифа

**Предназначение запроса:**  
Удаление существующего тарифа. Доступно только пользователю с ролью `ADMIN`.

**Запрос:**

```http
DELETE /loan-service/deleteTariff?id=1
Authorization: Bearer <JWT>
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Authorization` | `Bearer <JWT>` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**

| Параметр | Тип | Обязательный | Доп ограничения | Описание |
|---|---|---|---|---|
| `id` | long | Да | Нет | Идентификатор тарифа |

**Request body:**  
Отсутствует.

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

Тело ответа отсутствует.

**Возможные ответы при ошибках:**

| HTTP | Причина |
|---|---|
| `401 Unauthorized` | JWT отсутствует или недействителен |
| `403 Forbidden` | Пользователь не имеет роли `ADMIN` |
| `400 Bad Request` | Тариф с указанным `id` не найден |
| `400 Bad Request` | Не передан обязательный параметр `id` |

При отсутствии тарифа API формирует ошибку:

```json
{
  "error": {
    "code": "ORDER_NOT_FOUND",
    "message": "Заявка не найдена"
  }
}
```

> Примечание: в текущей реализации текст ошибки для отсутствующего тарифа использует сообщение `Заявка не найдена`.

---

# 3. Кредитные заявки

## 3.1. Создание кредитной заявки

**Предназначение запроса:**  
Создание новой кредитной заявки пользователя на выбранный тариф.

**Запрос:**

```http
POST /loan-service/order
Authorization: Bearer <JWT>
Content-Type: application/json
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Authorization` | `Bearer <JWT>` | Да |
| `Content-Type` | `application/json` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**

```json
{
  "userId": 1,
  "tariffId": 1
}
```

| Поле | Тип | Обязательное | Доп ограничения | Описание |
|---|---|---|---|---|
| `userId` | long | Да | Нет | Идентификатор пользователя |
| `tariffId` | long | Да | Нет | Идентификатор тарифа |

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

```json
{
  "data": {
    "orderId": "550e8400-e29b-41d4-a716-446655440000"
  }
}
```

После создания заявке присваивается статус `IN_PROGRESS`.

**Возможные ответы при ошибках:**

| HTTP | Код ошибки | Причина |
|---|---|---|
| `401 Unauthorized` | — | Отсутствует/недействителен JWT |
| `403 Forbidden` | — | Недостаточно прав |
| `400 Bad Request` | `USER_NOT_FOUND` | Пользователь не найден |
| `400 Bad Request` | `TARIFF_NOT_FOUND` | Тариф не найден |
| `400 Bad Request` | `LOAN_CONSIDERATION` | Заявка на данный тариф уже находится на рассмотрении |
| `400 Bad Request` | `LOAN_ALREADY_APPROVED` | Заявка на данный тариф уже одобрена |
| `400 Bad Request` | `TRY_LATER` | После отказа ещё не истёк период ожидания |

---

## 3.2. Получение статуса кредитной заявки

**Предназначение запроса:**  
Получение текущего статуса существующей кредитной заявки.

**Запрос:**

```http
GET /loan-service/getStatusOrder?orderId=550e8400-e29b-41d4-a716-446655440000
Authorization: Bearer <JWT>
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Authorization` | `Bearer <JWT>` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**

| Параметр | Тип | Обязательный | Доп ограничения | Описание |
|---|---|---|---|---|
| `orderId` | UUID | Да | Нет | Идентификатор заявки |

**Request body:**  
Отсутствует.

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

```json
{
  "data": {
    "orderStatus": "IN_PROGRESS"
  }
}
```

Возможные значения статуса:

```text
IN_PROGRESS
APPROVED
REFUSED
```

**Возможные ответы при ошибках:**

| HTTP | Код ошибки | Причина |
|---|---|---|
| `401 Unauthorized` | — | JWT отсутствует или недействителен |
| `403 Forbidden` | — | Недостаточно прав |
| `400 Bad Request` | `ORDER_NOT_FOUND` | Заявка не найдена |
| `400 Bad Request` | — | Не передан `orderId` |

---

## 3.3. Удаление кредитной заявки

**Предназначение запроса:**  
Удаление кредитной заявки пользователя.

Удаление выполняется только для заявки со статусом `IN_PROGRESS`.

**Запрос:**

```http
DELETE /loan-service/deleteOrder
Authorization: Bearer <JWT>
Content-Type: application/json
```

**Headers:**

| Header | Значение | Обязательный |
|---|---|---|
| `Authorization` | `Bearer <JWT>` | Да |
| `Content-Type` | `application/json` | Да |

**Path parameters:**  
Отсутствуют.

**Query parameters:**  
Отсутствуют.

**Request body:**

```json
{
  "userId": 1,
  "orderId": "550e8400-e29b-41d4-a716-446655440000"
}
```

| Поле | Тип | Обязательное | Доп ограничения | Описание |
|---|---|---|---|---|
| `userId` | long | Да | Нет | Идентификатор пользователя |
| `orderId` | UUID | Да | Нет | Идентификатор заявки |

**Ответ при успешном выполнении:**

```http
HTTP/1.1 200 OK
```

Тело ответа отсутствует.

**Возможные ответы при ошибках:**

| HTTP | Код ошибки | Причина |
|---|---|---|
| `401 Unauthorized` | — | JWT отсутствует или недействителен |
| `403 Forbidden` | — | Недостаточно прав |
| `400 Bad Request` | `ORDER_NOT_FOUND` | Заявка не найдена |
| `400 Bad Request` | `ORDER_IMPOSSIBLE_TO_DELETE` | Заявка не может быть удалена |
| `400 Bad Request` | — | Не передан обязательный параметр |

---

# 4. Общий формат ошибки

Для `CustomException` API использует HTTP `400 Bad Request`.

**Формат ответа:**

```json
{
  "error": {
    "code": "ERROR_CODE",
    "message": "Описание ошибки"
  }
}
```

Для ошибок таймаута используется:

```http
408 Request Timeout
```

с аналогичной структурой:

```json
{
  "error": {
    "code": "REQUEST_TIME_OUT: GET_TARIFFS",
    "message": "Не удалось получить тарифы. Превышено время ожидания"
  }
}
```

---

# 5. Авторизация

Защищённые endpoints используют JWT.

Токен передаётся в HTTP-заголовке:

```http
Authorization: Bearer <JWT>
```

JWT получается после успешного выполнения:

```http
POST /user/authenticate
```

и используется для последующих защищённых запросов.

## Роли

| Роль | Возможности |
|---|---|
| `USER` | Работа с собственными кредитными заявками |
| `ADMIN` | Административные операции с тарифами и заявками |

Получение списка тарифов доступно без авторизации.

---

# 6. Сводная таблица endpoints

| Метод | Endpoint | Авторизация | Назначение |
|---|---|---|---|
| `POST` | `/user/register` | Нет | Регистрация пользователя |
| `POST` | `/user/authenticate` | Нет | Авторизация пользователя |
| `GET` | `/loan-service/getTariffs` | Нет | Получение списка тарифов |
| `POST` | `/loan-service/addTariff` | `ADMIN` | Создание тарифа |
| `DELETE` | `/loan-service/deleteTariff?id={id}` | `ADMIN` | Удаление тарифа |
| `POST` | `/loan-service/order` | `ADMIN`* | Создание заявки |
| `GET` | `/loan-service/getStatusOrder?orderId={UUID}` | `USER`, `ADMIN` | Получение статуса заявки |
| `DELETE` | `/loan-service/deleteOrder` | `ADMIN`* | Удаление заявки |
```