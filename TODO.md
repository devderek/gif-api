Things left to do on this project:
- Unit tests
- Integration tests
- Add a means to auth a user using user credentials in the database
- Secure the collection modification and gif modification endpoints. The user ID in the path should provide auth to use those endpoints.
- Implement rate limiting for all endpoints
- Paginate all responses
- Utilize HATEOAS in all responses