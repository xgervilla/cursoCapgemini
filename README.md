# Curso de formación
## Spring, Maven, JavaScript, NodeJS, ReactJS

---
20 Marzo - 24 Abril

**Contenido:**
* **GildedRose**: Código con los tests de la refactorización de Gilded Rose.
* **catalogo**: Implementación backend de un catálogo de películas, desde la declaración y validación de entidades hasta el desarrollo de microservicios para su uso mediante clientes.
* **demo-maven**: Creación de un proyecto mediante un arquetipo.
* **demo-web**: Proyecto ejemplo de la implementación de una página web y la comunicación entre microservicios.
* **demo**: Proyecto con el desarrollo inicial y pruebas auxiliares del desarrollo backend.
* **fundamentos**: Proyecto con el desarrollo inicial de la parte frontend.
  * _index.html_: Implementación de una cuenta regresiva hasta una fecha seleccionada.
  * _calculadora.html_: Implementación de una calculadora básica.
* **lotes**: Proyecto ejemplo para probar la ejecución con batches de datos, steps y jobs.
* **ms.apigateway**: Ejemplo de servidor con redireccionamiento y gateway.
* **ms.eureka**: Ejemplo de servidor con balanceo de carga.
* **sparring_front**: Proyecto con distintas pruebas y desarrollo de tests para familiarizarse con los lenguajes de desarrollo frontend.
* **catalogo_postman_tests**: Test realizados sobre el proyecto _catalogo_ en Postgres.
* **rama.txt**: Archivo para testear el merge de branches

<br>
Cómo ejecutar los tests del postman desde terminal:

1. Descargar newman:

   <code>npm install newman</code>
2. Ejecutar con el archivo de los tests como parámetro: 

    <code>newman run catalogo_postman_tests</code>