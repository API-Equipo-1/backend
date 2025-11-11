# E-Commerce Backend API Documentation

## Base URL
```
http://localhost:8080
```

## Database
- **Type**: MySQL
- **Database Name**: `ecommerce_db`
- **Port**: 3306

---

## Authentication

### 🔐 JWT Token
All authenticated endpoints require a JWT token in the `Authorization` header:
```
Authorization: Bearer <your-jwt-token>
```

---

## CORS Configuration

The API accepts requests from the following origins:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)
- `http://localhost:5173` (Vite)

---

## Endpoints

### 📋 Table of Contents
1. [Authentication](#1-authentication)
2. [Productos (Products)](#2-productos-products)
3. [Categorías (Categories)](#3-categorías-categories)
4. [Usuarios (Users)](#4-usuarios-users)
5. [Pedidos (Orders)](#5-pedidos-orders)
6. [Direcciones (Addresses)](#6-direcciones-addresses)

---

## 1. Authentication

Base path: `/api/auth`

### Register User
**Endpoint:** `POST /api/auth/register`  
**Auth Required:** ❌ No  
**Description:** Register a new user account

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
"User registered successfully with ID: 1"
```

---

### Login
**Endpoint:** `POST /api/auth/login`  
**Auth Required:** ❌ No  
**Description:** Login and receive JWT token

**Request Body:**
```json
{
  "email": "juan.perez@example.com",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Note:** Save this token and include it in the `Authorization` header for authenticated requests.

---

## 2. Productos (Products)

Base path: `/api/productos`

### Get All Products
**Endpoint:** `GET /api/productos`  
**Auth Required:** ❌ No (Public)  
**Description:** Get list of all products

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombre": "Laptop HP",
    "descripcion": "Laptop HP 15.6 pulgadas",
    "precio": 899.99,
    "stock": 10,
    "categorias": [
      {
        "id": 1,
        "nombre": "Electrónica"
      }
    ]
  }
]
```

---

### Get Product by ID
**Endpoint:** `GET /api/productos/{id}`  
**Auth Required:** ❌ No (Public)  
**Description:** Get a specific product by ID

**URL Parameters:**
- `id` (Long) - Product ID

**Example:** `GET /api/productos/1`

**Response:** `200 OK`
```json
{
  "id": 1,
  "nombre": "Laptop HP",
  "descripcion": "Laptop HP 15.6 pulgadas",
  "precio": 899.99,
  "stock": 10,
  "categorias": [
    {
      "id": 1,
      "nombre": "Electrónica"
    }
  ]
}
```

**Error Response:** `404 NOT FOUND`
```json
{
  "status": 404,
  "error": "Producto no encontrado",
  "message": "No se encontró el producto con ID: 999"
}
```

---

### Create Product
**Endpoint:** `POST /api/productos`  
**Auth Required:** ✅ Yes  
**Description:** Create a new product

**Request Body:**
```json
{
  "nombre": "Mouse Inalámbrico",
  "descripcion": "Mouse ergonómico inalámbrico",
  "precio": 29.99,
  "stock": 50,
  "categorias": [
    {
      "id": 2,
      "nombre": "Accesorios"
    }
  ]
}
```

**Response:** `200 OK`
```json
{
  "id": 2,
  "nombre": "Mouse Inalámbrico",
  "descripcion": "Mouse ergonómico inalámbrico",
  "precio": 29.99,
  "stock": 50,
  "categorias": [
    {
      "id": 2,
      "nombre": "Accesorios"
    }
  ]
}
```

---

### Update Product
**Endpoint:** `PUT /api/productos/{id}`  
**Auth Required:** ✅ Yes  
**Description:** Update product price and stock

**URL Parameters:**
- `id` (Long) - Product ID

**Request Body:**
```json
{
  "precio": 39.99,
  "stock": 45
}
```

**Response:** `200 OK`
```json
{
  "precio": 39.99,
  "stock": 45
}
```

---

### Delete Product
**Endpoint:** `DELETE /api/productos/{id}`  
**Auth Required:** ✅ Yes  
**Description:** Delete a product

**URL Parameters:**
- `id` (Long) - Product ID

**Example:** `DELETE /api/productos/1`

**Response:** `204 NO CONTENT`

---

## 3. Categorías (Categories)

Base path: `/api/categorias`

### Get All Categories
**Endpoint:** `GET /api/categorias`  
**Auth Required:** ❌ No  
**Description:** Get list of all categories

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombre": "Electrónica",
    "productos": []
  },
  {
    "id": 2,
    "nombre": "Accesorios",
    "productos": []
  }
]
```

---

### Get Category by ID
**Endpoint:** `GET /api/categorias/{id}`  
**Auth Required:** ❌ No  
**Description:** Get a specific category by ID

**URL Parameters:**
- `id` (Long) - Category ID

**Example:** `GET /api/categorias/1`

**Response:** `200 OK`
```json
{
  "id": 1,
  "nombre": "Electrónica",
  "productos": []
}
```

---

### Create Category
**Endpoint:** `POST /api/categorias`  
**Auth Required:** ❌ No  
**Description:** Create a new category

**Request Body:**
```json
{
  "nombre": "Ropa"
}
```

**Response:** `200 OK`
```json
{
  "id": 3,
  "nombre": "Ropa",
  "productos": []
}
```

---

### Delete Category
**Endpoint:** `DELETE /api/categorias?id={id}`  
**Auth Required:** ❌ No  
**Description:** Delete a category

**Query Parameters:**
- `id` (Long) - Category ID

**Example:** `DELETE /api/categorias?id=3`

**Response:** `200 OK`

---

## 4. Usuarios (Users)

Base path: `/api/usuarios`

### Get All Users
**Endpoint:** `GET /api/usuarios`  
**Auth Required:** ✅ Yes  
**Description:** Get list of all users

**Response:** `200 OK`
```json
[
  {
    "usuario_id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "password": "$2a$10$...",
    "pedidos": []
  }
]
```

---

### Get User by ID
**Endpoint:** `GET /api/usuarios/{usuario_id}`  
**Auth Required:** ✅ Yes  
**Description:** Get a specific user by ID

**URL Parameters:**
- `usuario_id` (Long) - User ID

**Example:** `GET /api/usuarios/1`

**Response:** `200 OK`
```json
{
  "usuario_id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "password": "$2a$10$...",
  "pedidos": []
}
```

---

### Create User
**Endpoint:** `POST /api/usuarios`  
**Auth Required:** ✅ Yes  
**Description:** Create a new user

**Request Body:**
```json
{
  "nombre": "María",
  "apellido": "García",
  "email": "maria.garcia@example.com",
  "password": "password123"
}
```

**Response:** `201 CREATED`
```json
{
  "usuario_id": 2,
  "nombre": "María",
  "apellido": "García",
  "email": "maria.garcia@example.com",
  "password": "$2a$10$...",
  "pedidos": []
}
```

---

### Delete User
**Endpoint:** `DELETE /api/usuarios?id={id}`  
**Auth Required:** ✅ Yes  
**Description:** Delete a user

**Query Parameters:**
- `id` (Long) - User ID

**Example:** `DELETE /api/usuarios?id=2`

**Response:** `200 OK`

---

## 5. Pedidos (Orders)

Base path: `/api/pedidos`

### Get All Orders
**Endpoint:** `GET /api/pedidos`  
**Auth Required:** ✅ Yes  
**Description:** Get list of all orders

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "fecha": "2025-10-28T10:30:00",
    "estado": "PENDIENTE",
    "usuario": {
      "usuario_id": 1,
      "nombre": "Juan",
      "apellido": "Pérez",
      "email": "juan.perez@example.com"
    }
  }
]
```

---

### Get Order by ID
**Endpoint:** `GET /api/pedidos/{id}`  
**Auth Required:** ✅ Yes  
**Description:** Get a specific order by ID

**URL Parameters:**
- `id` (Long) - Order ID

**Example:** `GET /api/pedidos/1`

**Response:** `200 OK`
```json
{
  "id": 1,
  "fecha": "2025-10-28T10:30:00",
  "estado": "PENDIENTE",
  "usuario": {
    "usuario_id": 1,
    "nombre": "Juan",
    "apellido": "Pérez"
  }
}
```

---

### Create Order
**Endpoint:** `POST /api/pedidos`  
**Auth Required:** ✅ Yes  
**Description:** Create a new order

**Request Body:**
```json
{
  "fecha": "2025-10-28T10:30:00",
  "estado": "PENDIENTE",
  "usuario": {
    "usuario_id": 1
  }
}
```

**Response:** `200 OK`
```json
{
  "id": 2,
  "fecha": "2025-10-28T10:30:00",
  "estado": "PENDIENTE",
  "usuario": {
    "usuario_id": 1,
    "nombre": "Juan",
    "apellido": "Pérez"
  }
}
```

---

### Delete Order
**Endpoint:** `DELETE /api/pedidos/{id}`  
**Auth Required:** ✅ Yes  
**Description:** Delete an order

**URL Parameters:**
- `id` (Long) - Order ID

**Example:** `DELETE /api/pedidos/1`

**Response:** `200 OK`

---

## 6. Direcciones (Addresses)

Base path: `/api/direcciones`

### Get All Addresses
**Endpoint:** `GET /api/direcciones`  
**Auth Required:** ❌ No  
**Description:** Get list of all addresses

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "calle": "Av. Corrientes",
    "numero": "1234",
    "localidad": "Buenos Aires",
    "provincia": "Buenos Aires",
    "pais": "Argentina"
  }
]
```

---

### Get Address by ID
**Endpoint:** `GET /api/direcciones/{id}`  
**Auth Required:** ❌ No  
**Description:** Get a specific address by ID

**URL Parameters:**
- `id` (Long) - Address ID

**Example:** `GET /api/direcciones/1`

**Response:** `200 OK`
```json
{
  "id": 1,
  "calle": "Av. Corrientes",
  "numero": "1234",
  "localidad": "Buenos Aires",
  "provincia": "Buenos Aires",
  "pais": "Argentina"
}
```

---

### Create Address
**Endpoint:** `POST /api/direcciones`  
**Auth Required:** ❌ No  
**Description:** Create a new address

**Request Body:**
```json
{
  "calle": "Av. Santa Fe",
  "numero": "5678",
  "localidad": "Buenos Aires",
  "provincia": "Buenos Aires",
  "pais": "Argentina"
}
```

**Response:** `200 OK`
```json
{
  "id": 2,
  "calle": "Av. Santa Fe",
  "numero": "5678",
  "localidad": "Buenos Aires",
  "provincia": "Buenos Aires",
  "pais": "Argentina"
}
```

---

### Delete Address
**Endpoint:** `DELETE /api/direcciones?id={id}`  
**Auth Required:** ❌ No  
**Description:** Delete an address

**Query Parameters:**
- `id` (Long) - Address ID

**Example:** `DELETE /api/direcciones?id=2`

**Response:** `200 OK`

---

## Security Configuration

### Public Endpoints (No Authentication Required)
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/productos/**`
- `GET /api/categorias/**`
- `GET /api/direcciones/**`

### Protected Endpoints (Authentication Required)
- `POST /api/productos`
- `PUT /api/productos/**`
- `DELETE /api/productos/**`
- `/api/pedidos/**` (all methods)
- `/api/usuarios/**` (all methods)

### Admin Only Endpoints
- `/api/admin/**` (requires ROLE_ADMIN)

---

## Exception Handling & Error Responses

The API uses a global exception handler (`@ControllerAdvice`) to provide consistent error responses across all endpoints.

### HTTP Status Code Overview

| Status Code | Meaning | When It Occurs |
|-------------|---------|----------------|
| **400** | Bad Request | Invalid input data, negative prices, illegal arguments |
| **401** | Unauthorized | Missing or invalid authentication credentials |
| **403** | Forbidden | Valid credentials but insufficient permissions |
| **404** | Not Found | Resource doesn't exist in database |
| **500** | Internal Server Error | Unexpected server errors |

---

### 🔴 400 Bad Request

#### PrecioNegativoException
**When:** Attempting to set a negative price for a product  
**HTTP Status:** `400 BAD REQUEST`

**Response:**
```json
"El precio no puede ser negativo"
```

**Example Scenario:**
```json
PUT /api/productos/1
{
  "precio": -50.00,
  "stock": 10
}
```

---

#### IllegalArgumentException
**When:** Invalid arguments or business rule violations  
**HTTP Status:** `400 BAD REQUEST`

**Response:**
```json
"El id es del admin"
```

**Example Scenarios:**
- Trying to access/modify admin user (ID: 0)
- Invalid input parameters
- Business logic violations

---

### 🔴 401 Unauthorized

#### JwtAuthenticationException
**When:** JWT token authentication fails  
**HTTP Status:** `401 UNAUTHORIZED`

**Response:**
```json
"Invalid JWT token"
```

**Common Causes:**
- Token is malformed
- Token signature is invalid
- Token header is missing

---

#### CustomAuthenticationException
**When:** User authentication fails  
**HTTP Status:** `401 UNAUTHORIZED`

**Response:**
```json
"Invalid credentials"
```

**Common Causes:**
- Incorrect email/password combination
- User account doesn't exist
- Authentication service error

**Example:**
```json
POST /api/auth/login
{
  "email": "wrong@example.com",
  "password": "wrongpassword"
}

Response: 401 Unauthorized
"Invalid credentials"
```

---

#### Security Configuration 401
**When:** Accessing protected endpoint without authentication  
**HTTP Status:** `401 UNAUTHORIZED`

**Response:**
```json
{
  "status": 401,
  "error": "No autorizado",
  "message": "Se requiere autenticación para acceder a este recurso",
  "timestamp": 1730102400000
}
```

**Example:**
```bash
# Trying to create a product without JWT token
POST /api/productos
{
  "nombre": "Product",
  "precio": 99.99
}

Response: 401 Unauthorized
```

---

### 🔴 403 Forbidden

#### CustomAccessDeniedException
**When:** User is authenticated but lacks required permissions  
**HTTP Status:** `403 FORBIDDEN`

**Response:**
```json
"Access denied: insufficient permissions"
```

**Common Causes:**
- User doesn't have required role (e.g., ADMIN)
- Trying to access resources belonging to another user
- Token is valid but permissions are insufficient

---

#### Security Configuration 403
**When:** JWT token is invalid or expired  
**HTTP Status:** `403 FORBIDDEN`

**Response:**
```json
{
  "status": 403,
  "error": "Acceso denegado",
  "message": "Se requiere un token JWT válido para acceder a este recurso",
  "timestamp": 1730102400000
}
```

**Common Causes:**
- JWT token has expired (> 24 hours old)
- Token was tampered with
- Invalid token signature

---

### 🔴 404 Not Found

#### ProductoNotFoundException
**When:** Requested product doesn't exist in database  
**HTTP Status:** `404 NOT FOUND`

**Response:**
```json
"No se encontró el producto con id: 999"
```

**Example:**
```bash
GET /api/productos/999

Response: 404 Not Found
"No se encontró el producto con id: 999"
```

---

#### PedidoNotFoundException
**When:** Requested order doesn't exist in database  
**HTTP Status:** `404 NOT FOUND`

**Response:**
```json
"Pedido no encontrado 123"
```

**Example:**
```bash
GET /api/pedidos/123

Response: 404 Not Found
"Pedido no encontrado 123"
```

---

#### UsuarioNotFoundException
**When:** Requested user doesn't exist in database  
**HTTP Status:** `404 NOT FOUND`

**Response:**
```json
"Usuario no encontrado con id: 456"
```

**Example:**
```bash
GET /api/usuarios/456

Response: 404 Not Found
"Usuario no encontrado con id: 456"
```

---

### 🔴 500 Internal Server Error

#### Generic Exception Handler
**When:** Unexpected server errors occur  
**HTTP Status:** `500 INTERNAL SERVER ERROR`

**Response:**
```json
"Error interno: [detailed error message]"
```

**Common Causes:**
- Database connection failures
- Unhandled exceptions
- Server configuration issues
- Unexpected null values

**Example:**
```bash
Response: 500 Internal Server Error
"Error interno: Connection timeout to database"
```

---

## Exception Handling Examples by Endpoint

### Products Endpoints

| Endpoint | Possible Exceptions |
|----------|-------------------|
| `GET /api/productos/{id}` | 404 ProductoNotFoundException |
| `POST /api/productos` | 400 PrecioNegativoException<br>401 Unauthorized<br>403 Forbidden |
| `PUT /api/productos/{id}` | 400 PrecioNegativoException<br>404 ProductoNotFoundException<br>401 Unauthorized |
| `DELETE /api/productos/{id}` | 404 ProductoNotFoundException<br>401 Unauthorized |

---

### Orders Endpoints

| Endpoint | Possible Exceptions |
|----------|-------------------|
| `GET /api/pedidos/{id}` | 404 PedidoNotFoundException<br>401 Unauthorized |
| `POST /api/pedidos` | 400 IllegalArgumentException<br>401 Unauthorized |
| `DELETE /api/pedidos/{id}` | 404 PedidoNotFoundException<br>401 Unauthorized |

---

### Users Endpoints

| Endpoint | Possible Exceptions |
|----------|-------------------|
| `GET /api/usuarios/{id}` | 404 UsuarioNotFoundException<br>401 Unauthorized |
| `POST /api/usuarios` | 400 IllegalArgumentException<br>401 Unauthorized |
| `DELETE /api/usuarios/{id}` | 404 UsuarioNotFoundException<br>401 Unauthorized |

---

### Authentication Endpoints

| Endpoint | Possible Exceptions |
|----------|-------------------|
| `POST /api/auth/register` | 400 IllegalArgumentException<br>500 Internal Server Error |
| `POST /api/auth/login` | 401 CustomAuthenticationException<br>404 UsuarioNotFoundException |

---

## Error Response Format

### Simple String Response (Most Common)
```json
"Error message text"
```
**Used by:** All custom exceptions (ProductoNotFoundException, PrecioNegativoException, etc.)

---

### Structured JSON Response (Security Errors)
```json
{
  "status": 401,
  "error": "No autorizado",
  "message": "Se requiere autenticación para acceder a este recurso",
  "timestamp": 1730102400000
}
```
**Used by:** Security filter chain (401 Unauthorized, 403 Forbidden)

---

## Best Practices for Frontend Error Handling

### Example: Handling Errors with Axios

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Add token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt-token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle errors globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 400:
          console.error('Bad Request:', error.response.data);
          // Show validation error to user
          break;
        case 401:
          console.error('Unauthorized:', error.response.data);
          // Redirect to login
          localStorage.removeItem('jwt-token');
          window.location.href = '/login';
          break;
        case 403:
          console.error('Forbidden:', error.response.data);
          // Show "Access Denied" message
          break;
        case 404:
          console.error('Not Found:', error.response.data);
          // Show "Resource not found" message
          break;
        case 500:
          console.error('Server Error:', error.response.data);
          // Show generic error message
          break;
        default:
          console.error('Error:', error.response.data);
      }
    }
    return Promise.reject(error);
  }
);

export default api;
```

---

### Example: Specific Error Handling

```javascript
const createProduct = async (productData) => {
  try {
    const response = await api.post('/productos', productData);
    return response.data;
  } catch (error) {
    if (error.response) {
      const status = error.response.status;
      const message = error.response.data;

      if (status === 400 && message.includes('precio')) {
        alert('Error: El precio no puede ser negativo');
      } else if (status === 401) {
        alert('Sesión expirada. Por favor inicia sesión nuevamente.');
        // Redirect to login
      } else if (status === 404) {
        alert('Producto no encontrado');
      } else {
        alert(`Error: ${message}`);
      }
    } else {
      alert('Error de conexión con el servidor');
    }
    throw error;
  }
};
```

---

### Example: React Error Handling Hook

```javascript
import { useState } from 'react';

export const useApiError = () => {
  const [error, setError] = useState(null);

  const handleError = (error) => {
    if (error.response) {
      const { status, data } = error.response;
      
      const errorMap = {
        400: { type: 'validation', message: data },
        401: { type: 'auth', message: 'Sesión expirada' },
        403: { type: 'forbidden', message: 'Acceso denegado' },
        404: { type: 'notFound', message: data },
        500: { type: 'server', message: 'Error del servidor' }
      };

      setError(errorMap[status] || { type: 'unknown', message: data });
    } else {
      setError({ type: 'network', message: 'Error de conexión' });
    }
  };

  const clearError = () => setError(null);

  return { error, handleError, clearError };
};
```

---

## Frontend Integration Examples

### JavaScript/Fetch

#### Login Example
```javascript
const login = async (email, password) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password }),
  });
  
  const token = await response.text();
  localStorage.setItem('jwt-token', token);
  return token;
};
```

#### Get Products (Public)
```javascript
const getProducts = async () => {
  const response = await fetch('http://localhost:8080/api/productos');
  const products = await response.json();
  return products;
};
```

#### Create Product (Authenticated)
```javascript
const createProduct = async (productData) => {
  const token = localStorage.getItem('jwt-token');
  
  const response = await fetch('http://localhost:8080/api/productos', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(productData),
  });
  
  return await response.json();
};
```

---

### React/Axios

#### Axios Configuration
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Add token to every request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt-token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
```

#### Usage
```javascript
// Login
const login = async (email, password) => {
  const response = await api.post('/auth/login', { email, password });
  localStorage.setItem('jwt-token', response.data);
  return response.data;
};

// Get products
const getProducts = async () => {
  const response = await api.get('/productos');
  return response.data;
};

// Create order
const createOrder = async (orderData) => {
  const response = await api.post('/pedidos', orderData);
  return response.data;
};
```

---

### Angular

#### Service Example
```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createProduct(product: any): Observable<any> {
    const token = localStorage.getItem('jwt-token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.post<any>(this.apiUrl, product, { headers });
  }
}
```

---

## Notes

1. **JWT Token Expiration**: Tokens expire after 24 hours (86000000ms)
2. **Password Encryption**: Passwords are encrypted using BCrypt
3. **CORS**: Already configured for local frontend development
4. **Database**: Make sure MySQL is running on port 3306 with database `ecommerce_db`

---

## Testing the API

### Using cURL

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Test","apellido":"User","email":"test@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# Get Products (Public)
curl http://localhost:8080/api/productos

# Create Product (Authenticated)
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"nombre":"Test Product","descripcion":"Test","precio":99.99,"stock":10,"categorias":[]}'
```

---

## Contact & Support

For any questions about the API, please contact the backend team.

**Last Updated:** October 28, 2025
