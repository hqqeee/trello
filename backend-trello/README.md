All API calls are private except those that begin with `/api/auth`.

## API Calls for Authentication

This table shows the API calls available for authentication. 

| Route              | Method | Request Body                                   | Response                                                                                   |
|--------------------|--------|------------------------------------------------|--------------------------------------------------------------------------------------------|
| `/api/auth/signup` | POST   | `{email: "validemail@email.com", password: "password"}` | `{"result": "Created", "id": 132}`                                                               |
| `/api/auth/signin` | POST   | `{email: "validemail@email.com", password: "password"}` | `{"result": "Authorized", "token": "eyJhbGci.....xI0KfZg", "refreshToken": "145fc104-924d-4394-8548-f6373deb52e6"}` |
| `/api/auth/refresh`| POST   | `{"refreshToken": "145fc104-924d-4394-8548-f6373deb52e6"}`  | `{"accessToken": "eyJhbGlhQVYd....kjLw6OCuCrLQ", "refreshToken": "145fc104-924d-4394-8548-f6373deb52e6"}` |

### Description

#### /api/auth/signup
This API call is used to create a new user account. The user must provide a valid email address and password in the request body. If successful, the response will contain a message indicating that the user account was created, along with the ID of the newly created account.

#### /api/auth/signin
This API call is used to sign in an existing user. The user must provide their email address and password in the request body. If the credentials are correct, the response will contain a message indicating that the user has been authorized, along with an access token and a refresh token.

#### /api/auth/refresh
This API call is used to obtain a new access token using a refresh token. The user must provide a valid refresh token in the request body. If successful, the response will contain a new access token and a new refresh token.


### Board Management APIs
These APIs are used for managing boards in the application.

| Route                | Method | Request Body                                       | Response Body                                                                                                                |
|----------------------|--------|----------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `/api/board`         | GET    | -                                                  | `{"boards":[{id: 4, title: "todo1s", custom: {color: "desc1"}}, {...}]}`                                                     |
| `/api/board/{boardId}` | GET    | -                                                  | `{"title": "todo1s", "custom": {"color": "desc1"}, "lists": [{...}, {...}]}` |
| `/api/board/{boardId}` | DELETE | -                                                  | `{"message": "Deleted"}`                                                                                                     |
| `/api/board/{boardId}` | PUT    | `{"id": 5, "title": "newTitle", "custom": {...}}`   | `{"message": "Updated"}`                                                                                                     |
| `/api/board`         | POST   | `{"title": "newTitle", "custom": {...}}`           | `{"message": "Created", "id": 11}`                                                                                           |
| `/api/board//{boardId}/user/{userId}` | GET | - | `{"id": 3,"email": "email@email.com"}`|

### Description

#### GET /api/board
This API call retrieves a list of all boards. The response body contains an array of board objects, each containing an ID, title, and custom fields.

#### GET /api/board/{boardId}
This API call retrieves a specific board by its ID. The response body contains the board's title, custom fields, users assigned to the board, and a list of lists (columns) and their associated cards.

#### DELETE /api/board/{boardId}
This API call deletes a specific board by its ID. The response body contains a message indicating that the board was successfully deleted.

#### PUT /api/board/{boardId}
This API call updates a specific board by its ID. The request body should contain the updated board information, including the title and custom fields. The response body contains a message indicating that the board was successfully updated.

#### POST /api/board
This API call creates a new board. The request body should contain the title and custom fields for the new board. The response body contains


### List Management APIs
These APIs are used for managing lists on boards.

| Route                                | Method | Request Body                                         | Response Body          |
|--------------------------------------|--------|------------------------------------------------------|------------------------|
| `/api/board/{boardId}/list`          | POST   | {"title": "newTitle", "position": 1}                 | {"message": "Created"} |
| `/api/board/{boardId}/list`          | PUT    | [{"id": 1, "position": 1}, {"id": 0, "position": 2}] | {"message": "Updated"} |
| `/api/board/{boardId}/list/{listId}` | PUT    | {"title": "new Title", "position": 1}                | {"message": "Updated"} |
| `/api/board/{boardId}/list/{listId}` | DELETE | -                                                    | {"message": "Deleted"} |

### Description

#### POST /api/board/{boardId}/list
This API call creates a new list within a board. The request body should contain the title and position of the new list. The response body contains a message indicating that the list was successfully created.

#### PUT /api/board/{boardId}/list
This API call updates the positions of all lists within a board. The request body should contain an array of objects, each containing the ID and position of a list. The response body contains a message indicating that the lists were successfully updated.

#### PUT /api/board/{boardId}/list/{listId}
This API call updates a specific list within a board by its ID. The request body should contain the updated title and position of the list. The response body contains a message indicating that the list was successfully updated.

#### DELETE /api/board/{boardId}/list/{listId}
This API call deletes a specific list within a board by its ID. The response body contains a message indicating that the list was successfully deleted.


### Card Management APIs
These APIs are used for managing cards on boards.

| Route                                   | Method | Request Body                                                                                                   | Response Body                         |
|-----------------------------------------|--------|----------------------------------------------------------------------------------------------------------------|---------------------------------------|
| `/api/board/{boardId}/card`             | POST   | `{"title": "Title","list_id": 1,"position": 1,"desription": "desription","custom": {"customField": "h"} }`     | `{"message": "Created","id": 1 }`      |
| `/api/board/{boardId}/card`             | PUT    | `[{"id": 1,"position": 1,"list_id": 2 }, {"id": 2,"position": 1,"list_id": 1 }]`                                | `{"message": "Updated"}`              |
| `/api/board/{boardId}/card/{cardId}`    | PUT    | `{"title": "title","description": "description","list_id": 1 }`                                               | `{"message": "Updated"}`              |
| `/api/board/{boardId}/card/{cardId}`    | DELETE | -                                                                                                              | `{"message": "Deleted"}`              |
| `/api/board/{boardId}/card/{cardId}/users` | PUT | `{"add": [1,2,3],"remove": [4,5,6]}`                                             | `{"message": "Updated"}`              |

### Description

#### POST /api/board/{boardId}/card
This API call creates a new card within a board. The request body should contain the title, list ID, position, description, and custom fields of the new card. The response body contains a message indicating that the card was successfully created along with its ID.

#### PUT /api/board/{boardId}/card
This API call updates the positions and list IDs of all cards within a board. The request body should contain an array of objects, each containing the ID, position, and new list ID of a card. The response body contains a message indicating that the cards were successfully updated.

#### PUT /api/board/{boardId}/card/{cardId}
This API call updates a specific card within a board by its ID. The request body should contain the updated title, description, and list ID of the card. The response body contains a message indicating that the card was successfully updated.

#### DELETE /api/board/{boardId}/card/{cardId}
This API call deletes a specific card within a board by its ID. The response body contains a message indicating that the card was successfully deleted.

#### PUT /api/board/{boardId}/card/{cardId}/users
This API call adds or removes users to/from a specific card within a board by its ID. The request body should contain an object with "add" and/or "remove" arrays of user IDs. The response body contains a message indicating that the users were successfully added or removed from the card.