**Приложение "Анализатор Погоды"**

## Введение

Приложение "Анализатор Погоды" предоставляет информацию о текущей погоде в указанном городе, а также среднюю температуру для заданного временного диапазона. Приложение использует сторонний API погоды для получения данных о погоде в режиме реального времени и сохраняет их в базе данных.

## Начало работы

### Требования

- Java 8 или выше
- Docker (по желанию, для запуска базы данных)

### Конечные точки API

#### 1. Получение текущей погоды

- При запуске программы необходимо ввести город, по которому будет получена информация
- **Конечная точка**: `GET /latest-weather/{city}`   
- **Описание**: Извлекает информацию о последней погоде для указанного города.
- **Пример**: `http://localhost:8080/latest-weather/Minsk`

#### 2. Получение средней температуры

- **Конечная точка**: `GET /average-temperature/{fromDate}/{toDate}`
- **Описание**: Извлекает среднюю температуру для указанного временного диапазона.
- **Параметры**:
    - `city`: Название города.
- **Пример**: `http://localhost:8080/average-temperature/2022-12-01/2023-12-03`

### Примеры запросов и ответов

#### Текущая Погода

Запрос:
```bash
curl -X GET "http://localhost:8080/latest-weather/Minsk"
```

Ответ:
```json
{
  "temperature": -5.14,
  "windSpeed": 2.71,
  "pressure": 1009,
  "humidity": 98,
  "weatherCondition": "overcast clouds",
  "location": "Minsk"
}
```

#### Средняя Температура

Запрос:
```bash
curl -X GET "http://localhost:8080/average-temperature/2022-12-01/2023-12-02"
```

Ответ:
```json
{
  "2023-12-02" : "1.6947272727272729"
}
```

## Обработка Ошибок

В случае ошибки приложение предоставляет понятные сообщения об ошибке в ответе, а также соответствующие HTTP-статус-коды.

## Логирование

Приложение регистрирует важные события и ошибки. Вы можете найти логи в файле логов приложения (например, `application.log`).

