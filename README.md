# Interview Calendar API
This API has as objective to be run as a microservice, in order 
to find matching timeslots for interviews that both the candidates and interviewers.

## Data Model
The Data Model it's composed by just one entity: User.
The User entity is characterized by the following attributes:
* ID
* Name
* Role

The role can only have two values: "CANDIDATE" and "INTERVIEWER".
All users have also associated, by a one to many relationship, time slots
which sets the User availability.

## Tecnhologies Used
* Spring Boot: Bootstrap framework used as it allowed me to quickly setup
the service and start developing upon it;
* Hibernate: ORM used to facilitate operations on the DB;
* PostresSQL: Standard SQL Database. I used it, as it is the one i'm most confortable with;
* Testcontainers: Library that i used to do integration tests. This will create a database in a container in order 
* to test all endpoints in all of their entirety.

## Installation
### Database
A PostgresSQL container can be run by running `db/buildAndRunDb.sh`. 
The schema will be automatically created by Hibernate on startup.

### API
The service can be started up by running
```
./mvwn spring-boot:run
```

### Endpoints
This API supports the following endpoints:
#### Create User - POST /users
This will create a new User based on the JSON body sent. 
When creating an id will be generated.

Possible responses:
* 201 - Created successfully
* 400 - Bad request. JSON was malformed.

example of request:
```
curl --location --request POST 'localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"David",
    "role": "INTERVIEWER"
}'
```

with an example response:
```
{
    "id": 11,
    "name": "David",
    "role": "INTERVIEWER",
    "availableSlots": []
}
```
### Set User available time slots - PUT /timeslots/{id}
Sets the available time slots for a specific user.
Has ID as a path parameter, which is the ID of the user
that will be updated.

Possible responses:
* 201 - Created successfully
* 404 - User not found.
* 400 - body json malformated. This can also be returned if 
a time slot has an ending time before starting time.

Example of request:
```
curl --location --request PUT 'localhost:8080/users/timeslots/9' \
--header 'Content-Type: application/json' \
--data-raw '{
    "availableSlots": [
        {
            "starting_time": "2021-10-06 12:00",
            "ending_time": "2021-10-06 18:00"
        },
        {
            "starting_time": "2021-10-04 12:00",
            "ending_time": "2021-10-04 18:00"
        },
        {
            "starting_time": "2021-10-05 09:00",
            "ending_time": "2021-10-05 12:00"
        },
        {
            "starting_time": "2021-10-07 09:00",
            "ending_time": "2021-10-07 12:00"
        }
    ]
}'
```

example of response:
```
{
    "id": 12,
    "name": "David",
    "role": "INTERVIEWER",
    "availableSlots": [
        {
            "starting_time": "2021-10-04 09:00",
            "ending_time": "2021-10-04 16:00"
        },
        {
            "starting_time": "2021-10-05 09:00",
            "ending_time": "2021-10-05 16:00"
        },
        {
            "starting_time": "2021-10-06 09:00",
            "ending_time": "2021-10-06 16:00"
        },
        {
            "starting_time": "2021-10-07 09:00",
            "ending_time": "2021-10-07 16:00"
        },
        {
            "starting_time": "2021-10-08 09:00",
            "ending_time": "2021-10-08 16:00"
        }
    ]
}
```

### Find possible interview slots - POST /interviews_availability
Finds a matching time slot based on the availability of 
a candidate and a list of interviewers.


Possible responses:
* 201 - Created successfully
* 404 - User not found.
* 400 - body json malformated 
or a user was given for a wrong role 

Example of request:
```
{
    "candidate": 19,
    "interviewers": [
        12,
        18
    ]
}
```

example of response: 
```
{
    "candidate": "Carl",
    "possibleInterviewSlots": [
        {
            "starting_time": "2021-10-05 09:00",
            "ending_time": "2021-10-05 10:00"
        },
        {
            "starting_time": "2021-10-07 09:00",
            "ending_time": "2021-10-07 10:00"
        }
    ]
}
```

## Improvements:
* Improvement upon Dockerfile;
* Implementation of Swagger for a better API Documentation;
* Fix bug of: exception handler for custom exceptions is not working,
which always results in an error 500 being returned;
* Improvement of integration tests to also verify validations
such as 404 errors, wrong time slots being sent and when querying 
for possible interview slots and users with the wrong role being given.
