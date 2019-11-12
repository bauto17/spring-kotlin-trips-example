# spring-kotlin-trips-example
Este proyecto es un ejemplo de un microservicio rest en kotlin usando spring framework y postgreSql

## Comenzar

### Prerrequisitos

* Docker
* Docker-compose

### Instalación

Construir el docker-compose con el siguiente comando
```
$ sudo docker-compose build
```

Ejecutar con el siguiente comando
```
$ sudo docker-compose up
```

## Proyecto

### Resumen
se desea hacer un microservicio funcione de catálogo de viajes para una aplicación de viajes en automóvil.

debe proveer los siguientes servicios
* Listar viajes
* Crear viajes
* Actualizar Viajes

### Arquitectura
El microservicio esta distribuido en una arquitectura por capas
* Controladores: que se usan como interfaz de comunicación
* Servicios: donde se tiene la lógica de negocio
* Repositorios: se usan para el acceso a datos

Usando interfaces en las capas de servicios y repositorios para separar la definición de la funcionalidad de la implementación 

### Jvm
En el Dockerfile se puede encontrar
```
java -jar -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=30 -Xms512m -Xmx512m -server -XX:+UseG1GC -XX:MaxGCPauseMillis=200 /code/build/libs/*.jar
```

* -Duser.timezone : usar el time zone del host
* -Dnetworkaddress.cache.ttl -Dnetworkaddress.cache.ttl : ttl caché del DNS, útil en caso en el que la DB se encuentre en Failover
* -Xms512m -Xmx512m : se usan para dar tamaño min y max al heap de la jvm, se recomienda que estén en el mismo valor, para asi evitar el tiempo de pedir recursos al host en situaciones de estrés
* -XX:+UseG1GC : especifica la estrategia del Garvage Collector
* -XX:MaxGCPauseMillis : se especifica un max de tiempo para que el GC pause la ejecucion.

### Base de datos
Se usa una unica tabla 'trips' para acceder a toda la informacion necesaria esta estrategia duplica alguna informacion al no estar normalizada, pero trae beneficios en velocidad

la busqueda pricipal se pagina usando la estrategia de conjuntos sobre la columna id, que limita el movimiento a paginas lejanas y el acceso a datos como cantidad de paginas y cantidad de registros, para mejorar la velociad de la operacion en grandes tablas

## Ejemplos de uso

listar viajes
```
curl -X GET  http://localhost:8080/trips
```

listar viajes filtrados
```
curl -X GET \
  'http://localhost:8080/trips?page=1&index=1_0&city=Cartagena' \
  -H 'cache-control: no-cache'
```

Creacion de viaje
```
curl -X POST \
  http://localhost:8080/trips \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
    "start": {
        "date": {
            "$date": "2018-12-05T09:45:24.000+0000"
        },
        "pickup_address": "Cra. 16 #94-37, Bogotá, Colombia",
        "pickup_location": {
            "type": "Point",
            "coordinates": [
                -74.0510944,
                4.6802152
            ]
        }
    },
    "end": {
        "date": null,
        "pickup_address": "Puerta 7-Salidas-Aeropuerto El Dorado",
        "pickup_location": {
            "type": "Point",
            "coordinates": [
                -74.1407656,
                4.6978672
            ]
        }
    },
    "country": {
        "name": "Colombia"
    },
    "city": {
        "name": "Bogotá"
    },
    "passenger": {
        "first_name": "Jorge Luis",
        "last_name": "Acevedo Escobar"
    },
    "driver": {
        "first_name": "William Steveng",
        "last_name": "Miranda Martinez "
    },
    "car": {
        "plate": "FRR391"
    },
    "status": "near",
    "checkCode": 0,
    "createdAt": {
        "$date": "2018-12-04T19:25:48.014-0500"
    },
    "updatedAt": {
        "$date": "2018-12-05T12:02:21.807-0500"
    },
    "price": 38500,
    "driver_location": {
        "type": "Point",
        "coordinates": [
            -74.04389530420303,
            4.750319657356549
        ]
    }
}'
```


## Authors

* **Sebastián Bautista** - *Initial work* - [Bauto17](https://github.com/bauto17)

## License

This project is licensed under the Apache License v2 - see the [LICENSE](LICENSE.md) file for details


