# spring-kotlin-trips-example
Este proyecto es un ejemplo de un microservicio rest en kotlin usando spring framework y postgreSql

## Comenzar

### Prerequisitos

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
se desea hacer un microservicio funcione de catalogo de viajes para una aplicacion de viajes en automovil.

debe proveer los siguientes servicios 
* Listar viajes
* Crear viajes
* Actualizar Viajes

### Arquitectura
El microservicio esta distribuido en una arquitectura por capas
* Controladores: que se usan como interfaz de comunicacion
* Servicios: donde se tiene la logica de negocio
* Repositorios: se usan para el acceso a datos

Usando interfaces en las capas de servicios y repositorios para separar la deficion de la funcionalidad de la implementacion  

### Jvm
En el Dockerfile se puede encontrar
```
java -jar -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=30 -Xms512m -Xmx512m -server -XX:+UseG1GC -XX:MaxGCPauseMillis=200 /code/build/libs/*.jar
```

* -Duser.timezone : usar el time zone del host
* -Dnetworkaddress.cache.ttl -Dnetworkaddress.cache.ttl : ttl cache del DNS, util en caso en el que la DB se encuentre en Failover 
* -Xms512m -Xmx512m : se usan para dar tamaño min y max al heap de la jvm, se recomienda que esten en el mismo valor, para asi evitar el tiempo de pedir recursos al host en situaciones de estres
* -XX:+UseG1GC : especifica la estrategia del Garvage Collector
* -XX:MaxGCPauseMillis : se especifica un max de tiempo para que el GC pause la ejecucion.

### base de datos
Se usa una unica tabla 'trips' para acceder a toda la informacion necesaria esta estrategia duplica alguna informacion al no estar normalizada, pero trae beneficios en velocidad

la busqueda pricipal se pagina usando la estrategia de conjuntos sobre la columna id, que limita el movimiento a paginas lejanas y el acceso a datos como cantidad de paginas y cantidad de registros, para mejorar la velociad de la operacion en grandes tablas 

## Authors

* **Sebastián Bautista** - *Initial work* - [Bauto17](https://github.com/bauto17)

## License

This project is licensed under the Apache License v2 - see the [LICENSE](LICENSE.md) file for details
