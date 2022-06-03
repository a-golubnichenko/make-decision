# Implementation of Spring REST Security with JWT on Kotlin

This is the Kotlin RESTful web services with full mechanism of user login and registration (security role-based authorization), and sample CRUD endpoints for Postgress database.
Keywords: Kotlin, Spring Boot, Spring Data JPA, Postgres, Hibernate, Flyway, JWT, JSON Web Token, Maven

# Login/Sigin & get JWT
Request:
```
POST /api/v1/user/login HTTP/1.1
Host: localhost:8181
Content-Type: application/json

{
	"username" : "admin",
	"password" : "admin"
}
```

Response:
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNDUyMjI5NiwiZXhwIjoxNjI0NjA4Njk2fQ.XS0uGZXFWdbPMeMpWn20WtRiWRmNC5QlAUvLyYfW-qRQtXVnAlILaRY7dEBCyr80axaIZF5zMXtu1IxSqJaimA",
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "roles": [
        "ROLE_ADMIN"
    ]
}
```

# Registration/Signup (admin role is requried to call this endpoint)
Request:
```
POST /api/v1/admin/register HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNDUyMTI0NCwiZXhwIjoxNjI0NjA3NjQ0fQ.h8Vb_-Xw2uBxBAle6tJQydiTFubnHmvyYwXPkpJe8q-p2g9tVh4itPgt_LR2d2c-UH8g5COoqheLq5RrNLsWnw

{
	"username" : "user2",
	"password":  "user2",
	"email" : "user2@example.com",
	"firstName" : "User 2",
	"lastName" : "User 2",
	"roles" : ["ROLE_MODERATOR"]
}
```

Response:
```
{
    "id": 3,
    "username": "user2",
    "firstName": "User 2",
    "lastName": "User 2",
    "email": "user2@example.com",
    "roles": [
        "ROLE_MODERATOR"
    ],
    "createdAt": "2021-06-24T14:11:53.735",
    "updatedAt": "2021-06-24T14:11:53.735"
}
```

# List users (sample of calling an authorized endpoint with pagination and sorting)
Request:
```
GET /api/v1/user?size=100&amp;page=0&amp;sort=username,asc HTTP/1.1
Host: localhost:8181
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNDUxMzY0MCwiZXhwIjoxNjI0NjAwMDQwfQ.PbgGgnkwaCUIUhYZfGbj5Kzfs_6REZgMt8F_h_y_ALFMJfOi49jonkzH302kzzx-vTd7hDcCeUP4TiWhUWkgiQ
```

Response:
```
{
    "content": [
        {
            "id": 1,
            "username": "admin",
            "firstName": "Admin",
            "lastName": "Admin",
            "email": "admin@example.com",
            "roles": [
                "ROLE_ADMIN"
            ],
            "createdAt": "2021-06-24T14:00:51.620785",
            "updatedAt": "2021-06-24T14:00:51.620785"
        },
        {
            "id": 2,
            "username": "user1",
            "firstName": "User 1",
            "lastName": "User 1",
            "email": "user1@example.com",
            "roles": [
                "ROLE_MODERATOR"
            ],
            "createdAt": "2021-06-24T14:01:40.966",
            "updatedAt": "2021-06-24T14:01:40.966"
        },
        {
            "id": 3,
            "username": "user2",
            "firstName": "User 2",
            "lastName": "User 2",
            "email": "user2@example.com",
            "roles": [
                "ROLE_MODERATOR"
            ],
            "createdAt": "2021-06-24T14:11:53.735",
            "updatedAt": "2021-06-24T14:11:53.735"
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "offset": 0,
        "pageSize": 100,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 3,
    "size": 100,
    "number": 0,
    "sort": {
        "unsorted": false,
        "sorted": true,
        "empty": false
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
```

# Manage questions
## List questions
Request:
``` 
GET /api/v1/question?size=100&amp;page=0 HTTP/1.1
Host: localhost:8181
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg
``` 


Response:
```
{
    "content": [
        {
            "id": 6,
            "userId": 1,
            "text": "What would I like to drink today?",
            "options": [],
            "createdAt": "2022-06-03T14:10:06.219",
            "updatedAt": "2022-06-03T14:12:57.502"
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 100,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "last": true,
    "totalPages": 1,
    "size": 100,
    "number": 0,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

## Add a question
Request:
``` 
POST /api/v1/question HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1Mzc1NjE2MSwiZXhwIjoxNjUzODQyNTYxfQ.fQNiz1SDovS8MdWWf5wJmJamiSHxVsXVAJDSuyiJybrN4Bd7DlwrmsFQX8iLU3Vbsh_p7YCwGytNIux3dV7tJw

{
  "text" : "What do I want to drink today?"
}
``` 


Response:
```
{
    "id": 6,
    "userId": 1,
    "text": "What do I want to drink today?",
    "options": [],
    "createdAt": "2022-06-03T14:10:06.219",
    "updatedAt": "2022-06-03T14:10:06.219"
}
```

## Edit a question
Request:
``` 
PUT /api/v1/question/6 HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg

{
	"text" : "What would I like to drink today?"
}
``` 


Response:
```
{
    "id": 6,
    "userId": 1,
    "text": "What would I like to drink today?",
    "options": [],
    "createdAt": "2022-06-03T14:10:06.219",
    "updatedAt": "2022-06-03T14:12:57.502"
}
```

## Delete a question
Request:
``` 
DELETE /api/v1/question/6 HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg
``` 

# Manage options for a question
## Add an option for a question
Request:
``` 
POST /api/v1/question/5/option HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg

{
  "text" : "tea"
}
``` 


Response:
```
{
    "id": 5,
    "questionId": 5,
    "text": "tea",
    "active": true,
    "createdAt": "2022-06-03T14:19:59.475",
    "updatedAt": "2022-06-03T14:19:59.475"
}
```

## Edit an option of a question
Request:
``` 
PUT /api/v1/question/5/option/5 HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg

{
	"text" : "cofee",
	"active" : false
}
``` 


Response:
```
{
    "id": 5,
    "questionId": 5,
    "text": "cofee",
    "active": false,
    "createdAt": "2022-06-03T14:19:59.475",
    "updatedAt": "2022-06-03T14:21:54.339"
}
```

## Delete an option of a question
Request:
``` 
DELETE /api/v1/question/5/option/5 HTTP/1.1
Host: localhost:8181
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1NDI1NDU2MywiZXhwIjoxNjU0MzQwOTYzfQ.rShJ4tqbyrWvJ48Pv1Og7kYzMYp5g2pIyryde0a_-r4pdswAU5ocdE1oOxvPV7OTCK8WFEZQfGR7d3r2247cpg
``` 

# Sample of calling anonymous endpoint
Request:
``` 
GET /api/v1/noop HTTP/1.1
Host: localhost:8181
``` 
