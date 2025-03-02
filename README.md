# Life PM

This is a personal project inspired by Trello, Jira, and other Todo applications. Even though the project started for personal use, I decided it could work as a "tech demo" as well. However, since the main goal is still personal usage, there will likely be areas where it doesn't follow "industry" standards.

### Some Implementation Details
- **Database:** PostgreSQL
- **Application:**
    - JPA ORM, Hibernate
    - Spring Boot
    - MVC structure
    - Authentication: JWT auth and refresh token
    - File upload storage: Can be configured to Minio, DB, or the file system
- **UI (Different repo):** Angular
- **Testing:**
    - Cucumber (Planned for main coverage and business logic testing)
    - Other available solutions for "sandboxing":
        - H2 in-memory integration tests
        - Real database connection tests
        - Controller tests
- **CI/CD:**
    - The app can be run from the IDE (I use IntelliJ) but requires PostgreSQL
    - Docker Compose with a main local profile and for Raspberry Pi 5 (see the deploy scripts)

### Work in Progress

Planned but not yet implemented technical debt:

- DB initialization/changes with Flyway scripts
- Entity deletions with proper cleanup (files, related entities, etc.)
- Proper logging
- Better test coverage
- Some sort of user documentation

### License 
All Rights Reserved