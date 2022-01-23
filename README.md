#API Products

# Instalación

Crear una instancia de PostgresSQL local. Para ello puede usar Docker

```
docker pull postgres:alpine
docker run --name postgres-0 -e POSTGRES_PASSWORD=root -d -p 5432:5432 postgres:alpine
docker exec -it postgres-0 bash
create database db_products;
```

Una vez verificada la conexión a postgres y que exista la base de datos db_products. Levantar proyecto springboot

## Postman

Importar colección ubicada en directorio /postman para probar la API

## Endpoints

### GET (SKU)

Obtiene un producto por Sku
- GET > localhost:8040/api/falabella/products/{sku}


### GET (ALL)

Obtiene todos los productos almacenados
- GET > localhost:8040/api/falabella/products/

### SAVE

Guarda un producto
- POST > localhost:8040/api/falabella/products/

```
REQUEST (Ejemplo)

{
    "sku": "FAL-88195229",
    "name": "Bicicleta Baltoro Aro 29",
    "brand": "JEEP",
    "size": "ST",
    "price": 100.000,
    "principalImageUrl": "https://falabella.scene7.com/is/image/Falabella/881952283_77",
    "otherImagesUrl": [
        "https://falabella.scene7.com/is/image/Falabella/881952283_4",
        "https://falabella.scene7.com/is/image/Falabella/881952283_2"
    ]
}
```

### UPDATE

Actualiza un producto
- PUT > localhost:8040/api/falabella/products/

```
REQUEST (Ejemplo)

{
    "sku": "FAL-88195229",
    "name": "Bicicleta Baltoro Aro 29",
    "brand": "JEEP",
    "size": "ST",
    "price": 100.000,
    "principalImageUrl": "https://falabella.scene7.com/is/image/Falabella/881952283_77",
    "otherImagesUrl": [
        "https://falabella.scene7.com/is/image/Falabella/881952283_4",
        "https://falabella.scene7.com/is/image/Falabella/881952283_2"
    ]
}
```

### DELETE (SKU)

Elimina un producto por Sku
- DELETE > localhost:8040/api/falabella/products/{sku}

