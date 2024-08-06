# Документация API для Спортивного Магазина

## Обзор
Эта документация API предоставляет конечные точки для управления категориями, скидками, изображениями, заказами, продуктами, отзывами и пользователями в спортивном магазине. В каждом разделе указаны конечные точки, параметры запросов и детали ответов.

## Аутентификация
Все конечные точки предполагают использование аутентификации. Пример использования:

```http
Authorization: Bearer <your-token>
```

## Категории

### Создание категории
**POST /api/categories**

**Тело запроса:**
```json
{
  "categoryName": "Футбол",
  "categoryDescription": "Футбольные товары"
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "categoryName": "Футбол",
  "categoryDescription": "Футбольные товары"
}
```

### Получение категории по ID
**GET /api/categories/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "categoryName": "Футбол",
  "categoryDescription": "Футбольные товары"
}
```

### Получение всех категорий
**POST /api/categories/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "categoryName")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "categoryName": "Футбол",
      "categoryDescription": "Футбольные товары"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Обновление категории
**PUT /api/categories/{id}**

**Тело запроса:**
```json
{
  "categoryName": "Баскетбол",
  "categoryDescription": "Баскетбольные товары"
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "categoryName": "Баскетбол",
  "categoryDescription": "Баскетбольные товары"
}
```

### Удаление категории
**DELETE /api/categories/{id}**

**Ответ:**
- Статус: 200 OK

## Скидки

### Создание скидки
**POST /api/discounts**

**Тело запроса:**
```json
{
  "code": "SUMMER21",
  "productId": 1,
  "percentage": 15,
  "expiryDate": "2024-12-31T23:59:59",
  "description": "Летняя скидка"
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "code": "SUMMER21",
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч",
    "price": 100.0
  },
  "percentage": 15,
  "expiryDate": "2024-12-31T23:59:59",
  "description": "Летняя скидка"
}
```

### Получение всех скидок
**POST /api/discounts/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "id")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "code": "SUMMER21",
      "productReadDTO": {
        "id": 1,
        "productName": "Футбольный мяч",
        "price": 100.0
      },
      "percentage": 15,
      "expiryDate": "2024-12-31T23:59:59",
      "description": "Летняя скидка"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Получение скидки по ID
**GET /api/discounts/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "code": "SUMMER21",
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч",
    "price": 100.0
  },
  "percentage": 15,
  "expiryDate": "2024-12-31T23:59:59",
  "description": "Летняя скидка"
}
```

### Обновление скидки
**PUT /api/discounts/{id}**

**Тело запроса:**
```json
{
  "code": "WINTER21",
  "productId": 1,
  "percentage": 20,
  "expiryDate": "2024-12-31T23:59:59",
  "description": "Зимняя скидка"
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "code": "WINTER21",
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч",
    "price": 100.0
  },
  "percentage": 20,
  "expiryDate": "2024-12-31T23:59:59",
  "description": "Зимняя скидка"
}
```

### Удаление скидки
**DELETE /api/discounts/{id}**

**Ответ:**
- Статус: 200 OK

## Изображения

### Загрузка изображения
**POST /api/images/{id}**

**Параметры запроса:**
- `id` (ID продукта)

**Тело запроса:**
- Файл изображения (multipart/form-data)

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "image": "image.jpg",
  "product": {
    "id": 1,
    "productName": "Футбольный мяч"
  }
}
```

### Получение изображения по ID
**GET /api/images/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "image": "<base64-encoded-image>"
}
```

### Получение всех изображений
**GET /api/images/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "id")
- `id` (ID продукта, необязательно)

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "image": "<base64-encoded-image>",
      "product": {
        "id": 1,
        "productName": "Футбольный мяч"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Удаление изображения
**DELETE /api/images/{id}**

**Ответ:**
- Статус: 200 OK

## Заказы

### Создание заказа
**POST /api/orders**

**Тело запроса:**
```json
{
  "userId": 1,
  "orderItemIds": [
    {
      "productId": 1,
      "quantity": 2
    }
  ],
  "status": "NEW"
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username": "user1"
  },
  "totalPrice": 200.0,
  "status": "NEW",
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Получение заказа по ID
**GET /api/orders/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username":

 "user1"
  },
  "totalPrice": 200.0,
  "status": "NEW",
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Получение всех заказов
**POST /api/orders/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "id")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "userReadDTO": {
        "id": 1,
        "username": "user1"
      },
      "totalPrice": 200.0,
      "status": "NEW",
      "createdDate": "2024-01-01T12:00:00",
      "lastModifiedDate": "2024-01-01T12:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Обновление заказа
**PUT /api/orders/{id}**

**Тело запроса:**
```json
{
  "userId": 1,
  "orderItemIds": [
    {
      "productId": 1,
      "quantity": 3
    }
  ],
  "status": "CONFIRMED"
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username": "user1"
  },
  "totalPrice": 300.0,
  "status": "CONFIRMED",
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Удаление заказа
**DELETE /api/orders/{id}**

**Ответ:**
- Статус: 200 OK

## Продукты
Для отображения цен в долларах и применения скидки, ваш запрос должен содержать следующие Headers:

```
Currency: USD
Discount: true
```
### Создание продукта
**POST /api/products**

**Тело запроса:**
```json
{
  "productName": "Футбольный мяч",
  "productDescription": "Качественный футбольный мяч",
  "price": 100.0,
  "stock": 10,
  "categoryId": 1
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "productName": "Футбольный мяч",
  "productDescription": "Качественный футбольный мяч",
  "price": 100.0,
  "stock": 10,
  "categoryDTO": {
    "id": 1,
    "categoryName": "Футбол"
  }
}
```

### Получение продукта по ID
**GET /api/products/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "productName": "Футбольный мяч",
  "productDescription": "Качественный футбольный мяч",
  "price": 100.0,
  "stock": 10,
  "categoryDTO": {
    "id": 1,
    "categoryName": "Футбол"
  }
}
```

### Получение всех продуктов
**POST /api/products/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "productName")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "productName": "Футбольный мяч",
      "productDescription": "Качественный футбольный мяч",
      "price": 100.0,
      "stock": 10,
      "categoryDTO": {
        "id": 1,
        "categoryName": "Футбол"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Обновление продукта
**PUT /api/products/{id}**

**Тело запроса:**
```json
{
  "productName": "Футбольный мяч Премиум",
  "productDescription": "Качественный футбольный мяч премиум класса",
  "price": 150.0,
  "stock": 5,
  "categoryId": 1
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "productName": "Футбольный мяч Премиум",
  "productDescription": "Качественный футбольный мяч премиум класса",
  "price": 150.0,
  "stock": 5,
  "categoryDTO": {
    "id": 1,
    "categoryName": "Футбол"
  }
}
```

### Удаление продукта
**DELETE /api/products/{id}**

**Ответ:**
- Статус: 200 OK

## Отзывы

### Создание отзыва
**POST /api/reviews**

**Тело запроса:**
```json
{
  "userId": 1,
  "productId": 1,
  "comment": "Отличный мяч!",
  "rating": 5
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username": "user1"
  },
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч"
  },
  "comment": "Отличный мяч!",
  "rating": 5,
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Получение отзыва по ID
**GET /api/reviews/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username": "user1"
  },
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч"
  },
  "comment": "Отличный мяч!",
  "rating": 5,
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Получение всех отзывов
**POST /api/reviews/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "id")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "userReadDTO": {
        "id": 1,
        "username": "user1"
      },
      "productReadDTO": {
        "id": 1,
        "productName": "Футбольный мяч"
      },
      "comment": "Отличный мяч!",
      "rating": 5,
      "createdDate": "2024-01-01T12:00:00",
      "lastModifiedDate": "2024-01-01T12:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Обновление отзыва
**PUT /api/reviews/{id}**

**Тело запроса:**
```json
{
  "userId": 1,
  "productId": 1,
  "comment": "Хороший мяч!",
  "rating": 4
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "userReadDTO": {
    "id": 1,
    "username": "user1"
  },
  "productReadDTO": {
    "id": 1,
    "productName": "Футбольный мяч"


  },
  "comment": "Хороший мяч!",
  "rating": 4,
  "createdDate": "2024-01-01T12:00:00",
  "lastModifiedDate": "2024-01-01T12:00:00"
}
```

### Удаление отзыва
**DELETE /api/reviews/{id}**

**Ответ:**
- Статус: 200 OK

## Пользователи

### Создание пользователя
**POST /api/users**

**Тело запроса:**
```json
{
  "username": "user1",
  "password": "password123",
  "email": "user1@example.com"
}
```

**Ответ:**
- Статус: 201 Created
- Тело ответа:
```json
{
  "id": 1,
  "username": "user1",
  "email": "user1@example.com"
}
```

### Получение пользователя по ID
**GET /api/users/{id}**

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "username": "user1",
  "email": "user1@example.com"
}
```

### Получение всех пользователей
**POST /api/users/all**

**Параметры запроса:**
- `page` (по умолчанию 0)
- `size` (по умолчанию 10)
- `sort` (по умолчанию "username")

**Тело запроса:**
```json
{
  "filter": {}
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "content": [
    {
      "id": 1,
      "username": "user1",
      "email": "user1@example.com"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

### Обновление пользователя
**PUT /api/users/{id}**

**Тело запроса:**
```json
{
  "username": "user1_updated",
  "email": "user1_updated@example.com"
}
```

**Ответ:**
- Статус: 200 OK
- Тело ответа:
```json
{
  "id": 1,
  "username": "user1_updated",
  "email": "user1_updated@example.com"
}
```

### Удаление пользователя
**DELETE /api/users/{id}**

**Ответ:**
- Статус: 200 OK

**Примечание:** Убедитесь, что вы аутентифицированы перед выполнением запросов к конечным точкам API.
