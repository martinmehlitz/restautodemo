# restautodemo

A REST server for managing car advertisements

## start
sbt run

## endpoints

### GET /advert?sort=price&sortAsc=false
Retrieve a list of all adverts in the system.
Optional query params:
* sort: default is "id", currently implemented "id" and "price"

## GET /advert/id
Retrieve a specific advert by id

## POST /advert
Creates a new advert object.

## PUT /advert/id
Updates an existing advert or creates one, if it did not exist yet.

## DELETE /advert/id
Deletes an advert


### OPEN Issues

* Implementation of error handling
* Database persistence
* Test automation
* validation (see required fields and fields only for used cars);
* use standard JSON date format for the **first registration** field.
* Service should be able to handle CORS requests from any domain.
