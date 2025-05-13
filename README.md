# Cadastro e Autênticação de Usuários

``` mermaid
classDiagram

    class User {
        + String login
        + String passwordHash
    }

    class HashAlgorithm {
        <<interface>>
        + String hash(String password)
        + boolean verify(String password, String hash)
    }

    class BCryptHashAlgorithm {
        + String hash(String password)
        + boolean verify(String password, String hash)
    }

    class UserService {
        - UserRepository repository
        - HashAlgorithm hashAlgorithm
        + register(String login, String password)
        + updatePassword(String login, String currentPassword, String newPassword)
        + authenticate(String login, String password)
    }

    class UserRepository {
        <<interface>>
        + void save(User user)
        + void update(User user)
        + User findByLogin(String login)
    }

    class FileUserRepository {
        - String filePath
        - Map<String, User> users
        + void save(User user)
        + void update(User user)
        + User findByLogin(String login)
        - Scanner scanFile()
        - PrintWriter newPrintWriter()
        - void loadFromFile()
        - void saveToFile()
    }

    class UserAlreadyExistsException
    class UserNotFoundException
    class InvalidPasswordException
    class InvalidLoginException
    
    UserService --> UserRepository : uses
    UserService --> HashAlgorithm : uses
    
    HashAlgorithm <|.. BCryptHashAlgorithm : implements
    UserRepository <|.. FileUserRepository : implements
    
    UserService --> UserAlreadyExistsException : throws
    UserService --> UserNotFoundException : throws
    UserService --> InvalidPasswordException : throws
    UserService --> InvalidLoginException : throws


```

