# Тестовые сценарии API кредитного сервиса

Необходимые запросы для прохождения тест кейсов:
## Регистрация пользователя
```
POST http://localhost:8080/user/register
```
---
## Аутентификация
```
POST http://localhost:8080/user/authenticate
```
---
## Добавление тарифа
```
POST http://localhost:8080/loan-service/addTariff
```
---
## Удаление тарифа
```
DeLETE http://localhost:8080/loan-service/deleteTariff/tariff_id
```
---
## Добавление заявки
```
POST http://localhost:8080/loan-service/order
```
---
## Удаление заявки
```
DELETE http://localhost:8080/loan-service/deleteOrder
```
---
## Просмотр статуса заявки по её идентификатору
```
POST http://localhost:8080/loan-service/getStatusOrder
```
---
## Просмотр тарифов
```
GET http://localhost:8080/loan-service/getTariffs
```
---

Данные учётной записи админа:

Json Body
```
{
    "email": "ivanov@mail.ru",
    "password": "1234"
}
```
---

### AUTH-001 Успешная регистрация пользователя (Smoke)

steps:

| step | expected result |
|---|---|
| Отправить POST-запрос регистрации с валидными json ключами `firstname`, `lastname`, `email`, `password`. | HTTP 200. Тело ответа отсутствует. |
| Отправить запрос на аутентификацию, в json теле запроса передать email/password, которые использовались при регистрации. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-002 Регистрация с пустым значением ключа `firstname` в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос регистрации с пустым значением ключа `firstname` в json, Остальные поля валидны. | HTTP 4xx. Тело содержит `code=err`, `message="first name is required"`. |

### AUTH-003 Регистрация без ключа firstname в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос без ключа `firstname` в JSON. | HTTP 4xx. Сообщение о необходимости firstname. |

### AUTH-004 Регистрация с пустым значением ключа `lastname` в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос регистрации с пустым значением поля `lastname` в json, остальные поля валидны. | HTTP 4xx. `message="last name is required"`. |

### AUTH-005 Регистрация без ключа lastname в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос регистрации без ключа `lastname` в JSON. | HTTP 4xx. Возвращается сообщение о том, что lastname обязателен. |

### AUTH-006 Регистрация без ключа email в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос регистрации без ключа `email` в JSON. | HTTP 4xx. Возвращается сообщение о том, что email обязателен. |

### AUTH-007 Регистрация с пустым значением ключа email в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос на регистрацию с пустым значеним ключа `email` в json. | HTTP 4xx. Возвращается сообщение о необходимости email. |

### AUTH-008 Регистрация без ключа password в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на регистрацию без ключа `password` в json. | HTTP 4xx. Возвращается сообщение о том, что password обязателен. |

### AUTH-009 Регистрация с пустым значением ключа password в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на регистрацию с пустым значением ключа `password` в json. | HTTP 4xx. Возвращается сообщение о необходимости password. |

### AUTH-010 Регистрация без обязательных ключей в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на регистрацию без ключей `firstname`, `lastname`, `email`, `password` в json. | HTTP 4xx. API корректно обрабатывает обязательные поля согласно контракту. |

### AUTH-011 Регистрация с паролем длиной 7 символов (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением ключа password длиной 7 символов, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Password must be between 8 and 25 characters"`. |

### AUTH-012 Регистрация с паролем длиной 8 символов (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password из 8 символов, все остальные ключи заполнить валидными значениями. | HTTP 200. |
| Отправить запрос на аутентификацию, в json теле запроса передать email/password, которые использовались при регистрации. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-013 Регистрация с паролем длиной 9 символов (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password из 9 символов, все остальные ключи заполнить валидными значениями. | HTTP 200. |
| Отправить запрос на аутентификацию, в json теле запроса передать email/password, которые использовались при регистрации. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-014 Регистрация с паролем длиной 24 символа (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password из 24 символа, все остальные ключи заполнить валидными значениями. | HTTP 200. |
| Отправить запрос на аутентификацию, в json теле запроса передать email/password, которые использовались при регистрации. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-015 Регистрация с паролем длиной 25 символов (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password из 25 символов, все остальные ключи заполнить валидными значениями. | HTTP 200. |
| Отправить запрос на аутентификацию, в json теле запроса передать email/password, которые использовались при регистрации. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-016 Регистрация с паролем длиной 26 символов (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением ключа password длиной 26 символов, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Password must be between 8 and 25 characters"`. |

### AUTH-017 Регистрация с паролем, содержащим символ кириллицы в начале (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с валидным по длине значением ключа password, содержащим один символ кириллицы в начале, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-018 Регистрация с паролем, содержащим символ кириллицы в середине (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с валидным по длине значением ключа password, содержащим один символ кириллицы в середине, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-019 Регистрация с паролем, содержащим символ кириллицы в конце (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с валидным по длине значением ключа password, содержащим один символ кириллицы в конце, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-020 Регистрация со значением пароля, содержащим пробел в начале (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password, содержащим пробел в начале, все остальные ключи заполнить валидными данными. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-021 Регистрация со значеним пароля, содержащим пробел в середине (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением json ключа password вида `Abc 12345`. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-022 Регистрация со значением пароля, содержащим пробел в конце (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значеним json ключа password, содержащим пробел в конце, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Invalid format of password"`. |

### AUTH-023 Регистрация со значением email без `@` (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением email вида `testexample.com`, все остальные ключи заполнить валидными значениями. | HTTP 4xx. `message="Invalid email address"`. |

### AUTH-024 Регистрация с email без локальной части (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением ключа email вида `@example.com`, все остальные ключи заполнить валидными данными. | HTTP 4xx. `message="Invalid email address"`. |

### AUTH-025 Регистрация со значением email без домена (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значенем ключа email вида `test@`. | HTTP 4xx. `message="Invalid email address"`. |

### AUTH-026 Регистрация со значением email без точки в домене (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значенем ключа email вида `test@example`, все остальные ключи заполнить валидными данными. | HTTP 4xx. Возвращается ошибка валидации email. |

### AUTH-027 Регистрация со значением email, содержащим пробел (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением ключа email вида `test @example.com`. | HTTP 4xx. `message="Invalid email address"`. |

### AUTH-028 Регистрация со значением email, содержащим символы кириллицы (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации со значением email, содержащим символы кириллицы, все остальные ключи заполнить валидными данными. | HTTP 4xx. Возвращается ошибка валидации email. |

### AUTH-029 Регистрация со значением ключа email в верхнем регистре (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с валидным значением ключа email в верхнем регистре, все остальные поля заполнить валидными данными. | Email проходит форматную валидацию. HTTP 200. |

### AUTH-030 Повторная регистрация существующего email (Smoke)

steps:

| step | expected result |
|---|---|
| Отправить 2 раза одинаковый запрос на регистрацию с одним и тем же email, во всех остальных ключах – валидные данные. | HTTP 4xx. `message="Email already in used"`. Второй пользователь не создаётся. |

### AUTH-031 Повторная регистрация с тем же email в другом регистре (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на регистрацию пользователя со значением ключа email в нижнем регистре, затем со значением ключа email в верхнем регистре, в остальных ключах – валидные значения. | HTTP 4xx. `message="Email already in used"`. |

### AUTH-032 Регистрация с неизвестным дополнительным полем (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с произвольным полем в JSON, разрешённые по api контракту поля заполнить валидными данными. | API обрабатывает запрос согласно контракту; лишнее поле не приводит к 500. |

### AUTH-033 Регистрация с `null` firstname (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с null значением ключа `firstname`, все остальные ключи заполнить валидными данными. | HTTP 4xx. Поле определяется как обязательное. |

### AUTH-034 Регистрация с `null` email (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с null значением ключа `email`, все остальные ключи заполнить валидными данными. | HTTP 4xx. Поле определяется как обязательное. |

### AUTH-035 Регистрация с `null` password (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос регистрации с null значением ключа `password`, все остальные ключи заполнить валидными данными. | HTTP 4xx. Поле определяется как обязательное. |

### AUTH-036 Успешная аутентификация администратора (Smoke)

steps:

| step | expected result |
|---|---|
| Отправить запрос на аутентификацию, в json теле запроса передать корректные email/password  от учётной записи администратора. | HTTP 200. В теле ответа содержится поле token, значение которого можно передавать в заголовки защищённых запросов. |

### AUTH-037 Авторизация без ключа email в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос авторизации без ключа `email` в JSON. | HTTP 4xx. Возвращается сообщение о том, что email обязателен. |

### AUTH-038 Авторизация с пустым значением ключа email в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить post запрос на авторизацию с пустым значеним ключа `email` в json. | HTTP 4xx. Возвращается сообщение о необходимости email. |

### AUTH-039 Авторизация без ключа password в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на авторизацию без ключа `password` в json. | HTTP 4xx. Возвращается сообщение о том, что password обязателен. |

### AUTH-040 Авторизация с пустым значением ключа password в json (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на авторизацию с пустым значением ключа `password` в json. | HTTP 4xx. Возвращается сообщение о необходимости password. |

### AUTH-041 Авторизация с неверным password (Smoke)

steps:

| step | expected result |
|---|---|
| Отправить запрос авторизации с существующим email и неправильным паролем. | HTTP 4xx. В теле ответа – сообщение о том, что пользователь не найден. |

### AUTH-042 Авторизация несуществующего пользователя (Regress case)

steps:

| step | expected result |
|---|---|
| Отправить запрос на авторизацию со значением email, отсутствующим в БД. | HTTP 4xx. В теле ответа – сообщение Неверный email или пароль. |
---