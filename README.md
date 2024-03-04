# Car Rental App

### Description
Application is simple REST API for administration of cars, customers and rents. 

Used technology stack:
* Spring Boot 3.2.3
* Compatibility - Java 17
* Postgre database
* h2 inmemory database
* Swagger documentation


### Guides
The following guides illustrate how to use some features concretely:

* you can use REST endpoints by using Swagger documentation: http://localhost:8080/swagger-ui/index.html
* You can do GET/POST/PUT and DELETE operations over entities such as Car, Customer and Rental
* Whenever the Rental has defined both "rentedFromDate" and "rentedToDate", the app automatically calculates and saves "rentalPrice" based on the days of rental, RENTAL_RATE and RATE_ADJUST (which depends on priceCategory of the rented car). 
* All errors (if thrown) are being returned using standardized ErrorDto format
