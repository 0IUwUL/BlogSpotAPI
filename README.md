# BlogSpotAPI
TaskFlow is a RESTful API built from **[SpringBoot](https://spring.io/projects/spring-boot)**. 

## Requirements:
>- Java 17 or later
>- Maven 3.5+
>- PostgreSQL
>- Visual Studio Code 
>- Postman, Insomnia, or any API platform 

*Make to sure install Spring Boot extension pack and PostgreSQL extension for Visual Studio Code.*

## Initialization
1. Clone this repository.
2. Setup the *application.properties* and *SecurityConstants.java* file.

### application.properties (*./src/main/resources/*)
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

### SecurityConstants.java (*./src/main/java/security/*)
```
public class SecurityConstants {
    public static final long JWT_Expiration = ;
    public static final Key JWT_Secret = ;
}
```

**For more information in the setup, follow [Teddy Smith](https://www.youtube.com/@TeddySmithDev)  guide for the [application.properties](https://www.youtube.com/watch?v=Qk9_sglm8ik&list=PL82C6-O4XrHfX-kHudgC4cPfMy6QPaF-H&index=4) and [SecurityConstants.java](https://www.youtube.com/watch?v=M3OHzfRmJa0&list=PL82C6-O4XrHe3sDCodw31GjXbwRdCyyuY&index=9).**

3. Run the web application using the Spring Boot Extension.
4. Open a browser and visit the link http://localhost:8080.

# Test Cases
The test cases can be seen in **./src/test** where each controller, repository, and service functionality are tested.

## Registration
**Link**
> localhost:8080/auth/register
 
*JSON Content*
```
{
    "username": "Test",
    "password": "123"
}
```

*Expected Response*

```
User registered.
```
---
## Login
**Link**
> localhost:8080/auth/login
 
*JSON Content*
```
{
    "username": "Test",
    "password": "123"
}
```

*Expected Response*

```
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0IiwiaWF0IjoxNjk1OTc4OTIzLCJleHAiOjE2OTU5Nzg5MzB9.ez4xv2PaDSyi0y6aHcnjLFEvd3SKEn2ZrZJudRiGFfc",
    "tokenType": "Bearer "
}
```
---
## Post Functionality

### Get All Posts
**Link**
> localhost:8080/api/post/

*Expected Response*

```
{
    "content": [
        {
            "id": 4,
            "author_id": 4,
            "title": "League of Legends",
            "content": "MOBA Game",
            "tags": [
                "MOBA",
                "Online game"
            ],
            "createdOn": "2023-09-29T17:24:29.353378",
            "updatedOn": "2023-09-29T17:24:29.353378"
        },
        {
            "id": 3,
            "author_id": 2,
            "title": "Valorant",
            "content": "FPS Game",
            "tags": [
                "FPS",
                "New game"
            ],
            "createdOn": "2023-09-29T17:23:59.876157",
            "updatedOn": "2023-09-29T17:23:59.876157"
        },
        {
            "id": 2,
            "author_id": 3,
            "title": "Azur Lane",
            "content": "Fan service the best",
            "tags": [],
            "createdOn": "2023-09-29T17:23:47.525552",
            "updatedOn": "2023-09-29T17:23:47.525552"
        },
        {
            "id": 1,
            "author_id": 1,
            "title": "Azur Lane",
            "content": "Fan service the best",
            "tags": [],
            "createdOn": "2023-09-29T17:23:09.244577",
            "updatedOn": "2023-09-29T17:23:09.244577"
        }
    ],
    "pageNo": 0,
    "pageSize": 10,
    "totalElements": 4,
    "totalPages": 1,
    "last": true
}
```
### Get All Posts (With Parameters)
**Link**
> localhost:8080/api/post/?pageNo=1&pageSize=2

*Expected Response*

```
{
    "content": [
        {
            "id": 2,
            "author_id": 3,
            "title": "Azur Lane",
            "content": "Fan service the best",
            "tags": [],
            "createdOn": "2023-09-29T17:23:47.525552",
            "updatedOn": "2023-09-29T17:23:47.525552"
        },
        {
            "id": 1,
            "author_id": 1,
            "title": "Azur Lane",
            "content": "Fan service the best",
            "tags": [],
            "createdOn": "2023-09-29T17:23:09.244577",
            "updatedOn": "2023-09-29T17:23:09.244577"
        }
    ],
    "pageNo": 1,
    "pageSize": 2,
    "totalElements": 4,
    "totalPages": 2,
    "last": true
}
```

### Get Specific Post
**Link**
> localhost:8080/api/post/1

*Expected Response*

```
{
    "id": 3,
    "author_id": 2,
    "title": "Valorant",
    "content": "FPS Game",
    "tags": [
        "FPS",
        "New game"
    ],
    "createdOn": "2023-09-29T17:23:59.876157",
    "updatedOn": "2023-09-29T17:23:59.876157"
}
```
___
### Create Post
**Link**
> localhost:8080/api/post/create

*JSON Content*
```
{
    "title": "Fate Grand Order",
    "content": "One of the top grossing mobile games.",
    "tags": [],
    "author_id": 1
}
```
*Expected Response*

```
{
    "id": 6,
    "author_id": 1,
    "title": "Fate Grand Order",
    "content": "One of the top grossing mobile games.",
    "tags": [],
    "createdOn": "2023-09-29T17:30:00.498683",
    "updatedOn": "2023-09-29T17:30:00.498683"
}
```
### Create Post (with tags)
**Link**
> localhost:8080/api/post/create

*JSON Content*
```
{
    "title": "Fate Grand Order",
    "content": "One of the top grossing mobile games.",
    "tags": ["Gacha", "Online game"],
    "author_id": 1
}
```
*Expected Response*

```
{
    "id": 6,
    "author_id": 1,
    "title": "Fate Grand Order",
    "content": "One of the top grossing mobile games.",
    "tags": [
        "Gacha",
        "Online game"
    ],
    "createdOn": "2023-09-29T17:30:00.498683",
    "updatedOn": "2023-09-29T17:30:00.498683"
}
```
___
### Update Post
**Link**
> localhost:8080/api/post/update/1/1

*JSON Content*
```
{
    "title": "Update Title",
    "content": "Update Content",
    "tags": ["Update"]
}
```
*Expected Response*

```
{
    "id": 1,
    "author_id": 1,
    "title": "Update Title",
    "content": "Update Content",
    "tags": [
        "Update"
    ],
    "createdOn": "2023-09-29T17:23:09.244577",
    "updatedOn": "2023-09-29T18:01:22.313925"
}
```
___
### Delete Post
**Link**
> localhost:8080/api/post/delete/1/1

*Expected Response*

```
Post 1 has been deleted.
```
___
## Search Functionality
**Link**
> http://localhost:8080/api/post/search

*JSON Content*
```
{
    "title": "Blue Archive",
    "tags": []
}
```
*Expected Response*

```
{
    "content": [
        {
            "id": 4,
            "author_id": 4,
            "title": "League of Legends",
            "content": "MOBA Game",
            "tags": [
                "MOBA",
                "Online game"
            ],
            "createdOn": "2023-09-29T17:24:29.353378",
            "updatedOn": "2023-09-29T17:24:29.353378"
        }
    ],
    "pageNo": 0,
    "pageSize": 10,
    "totalElements": 1,
    "totalPages": 1,
    "last": true
}
```
___
## Profile Functionality
### Add Favorite Post
**Link**
> localhost:8080/api/user/favorite/1/post/3

*Expected Response*

```
Added to favorites
```
___
### Follower User
**Link**
> localhost:8080/api/user/follow/3/1

*Expected Response*

```
Added to following
```
### Get Favorite Post
**Link**
> localhost:8080/api/user/favorites/1

*Expected Response*

```
[
    {
        "id": 3,
        "author_id": 2,
        "title": "Valorant",
        "content": "FPS Game",
        "tags": [
            "FPS",
            "New game"
        ],
        "createdOn": "2023-09-29T17:23:59.876157",
        "updatedOn": "2023-09-29T17:23:59.876157"
    }
]
```
___
### View Profile
**Link**
> localhost:8080/api/user/profile/1

*Expected Response*

```
{
    "username": "Test",
    "posts": [
        {
            "id": 6,
            "author_id": 1,
            "title": "Fate Grand Order",
            "content": "One of the top grossing mobile games.",
            "tags": [
                "Online game",
                "Gacha"
            ],
            "createdOn": "2023-09-29T17:30:00.498683",
            "updatedOn": "2023-09-29T17:30:00.498683"
        }
    ],
    "follower_count": 0,
    "favorites": [
        {
            "id": 3,
            "author_id": 2,
            "title": "Valorant",
            "content": "FPS Game",
            "tags": [
                "FPS",
                "New game"
            ],
            "createdOn": "2023-09-29T17:23:59.876157",
            "updatedOn": "2023-09-29T17:23:59.876157"
        }
    ]
}
```