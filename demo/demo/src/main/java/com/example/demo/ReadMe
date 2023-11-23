Components:
Controller Class (AuthController):

Controllers handle incoming HTTP requests and define endpoints that the client can access.
In this project, AuthController contains endpoints related to user authentication and token generation.
Request and Response Classes:

These classes represent the structure of data sent in requests (LoginRequest, JwtResponse, etc.).
LoginRequest holds user credentials for login.
JwtResponse contains the JWT token returned to the client after successful authentication.
JwtTokenProvider:

Responsible for JWT token generation, validation, and parsing.
It creates a JWT token based on user details and validates tokens during requests.
CustomUserDetails and CustomUserDetailsService:

CustomUserDetails implements the UserDetails interface, holding user-specific data for authentication.
CustomUserDetailsService implements the UserDetailsService interface to load user information from a data source.
UserRepository and User Entity:

UserRepository handles interactions with the database (assumed to be present).
User represents the user entity with fields like username, password, and potentially other details.
What's Happening:
Login Endpoint (/api/auth/login):

This endpoint handles user login/authentication.
It takes user credentials (username and password) from the client (LoginRequest).
The AuthenticationManager authenticates the user based on provided credentials.
Upon successful authentication, a JWT token is generated using JwtTokenProvider.
The generated token is sent back to the client in the JwtResponse.
Refresh Token Endpoint (/api/auth/refresh):

This endpoint is responsible for refreshing an access token using a refresh token (not necessarily part of the code provided).
It verifies the validity of the refresh token.
If the refresh token is valid, a new access token is generated and sent back to the client.
User Registration Endpoint (/api/auth/register):

This endpoint allows users to register in the system.
It checks if the username is available (not already taken).
If the username is available, it saves the new user to the database after encoding the password.
Logout Endpoint (/api/auth/logout):

This endpoint is used for a logout action on the client side.
However, since JWT tokens are stateless, it might be used to perform actions like clearing tokens from local storage or marking tokens as invalid in the client application state.

