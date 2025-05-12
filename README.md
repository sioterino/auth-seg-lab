# Cadastro e Autênticação de Usuários

``` mermaid
classDiagram

    class User {
        - String login
        - char password
    }
    
    class UserService {
        + UserService( String algorithm)
        + register(String login, char currentPassword, char newPassword)
        + updatePassword()
        + authenticate()
    }
    
    class UserRepository {
        <<interface>>
        + List~User~ database
        + save(User user)
        + update(User user)
        + findByLogin(String login) User | null
    }
    
    class UserAlreadyExistsException
    class UserNotFoundException
    class InvalidPasswordException
    class InvalidLoginException
    
    User <.. UserService : Dependency
    UserService --> UserRepository : Association
    
    UserService --> UserAlreadyExistsException : throws
    UserService --> UserNotFoundException : throws
    UserService --> InvalidPasswordException : throws
    UserService --> InvalidLoginException : throws

```

