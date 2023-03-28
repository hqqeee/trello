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