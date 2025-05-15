# 1. Sistema de Autenticação de Usuários

Neste laboratório, iremos implementar um sistema de autenticação de usuários em Java. Apesar da simplicidade do sistema, serão vistas as boas práticas de segurança na implementação de autenticação de usuários, como o uso de algoritmos de hash seguros e a separação das camadas de persistência. Os códigos apresentados na **Seção 2** devem ser utilizados como base para a implementação do referido sistema.

## 1.1 Requisitos Funcionais

Requisitos funcionais são as funcionalidades que o sistema deve apresentar, ou seja, aquilo que o cliente espera que o sistema faça. São descritos em termos de entradas, processamento e saídas do sistema.

Os requisitos funcionais do sistema de autenticação de usuários são:

1. O sistema deve permitir o cadastro de novos usuários, com login e senha.
2. O sistema deve permitir a atualização da senha de um usuário.
3. O sistema deve permitir a autenticação de um usuário, ou seja, verificar se o login e a senha informados estão corretos.

## 1.2 Requisitos Arquiteturais

Os requisitos arquiteturais são um tipo de requisito não funcional que influenciam o projeto da arquitetura do sistema. Definem restrições ou qualidades que o sistema deve ter para garantir que ele seja, por exemplo, fácil de manter, escalável, seguro, eficiente, entre outros.

Os requisitos arquiteturais do sistema de autenticação de usuários são:

1. O sistema deve ter uma classe `User` com os atributos `login` e `password`.
   <br><br>
2. O sistema deve ser implementado com o padrão de projeto **Service**, que separa a lógica de negócio da lógica de apresentação (interface com o usuário).
   <br><br>
   * A classe `UserService` deve ter os seguintes métodos:
        - `register`: para cadastrar um novo usuário. Caso o login já exista, retornar uma exceção personalizada (ex: `UserAlreadyExistsException`);
        - `updatePassword`: para atualizar a senha de um usuário. Caso o usuário não exista, retornar uma exceção personalizada (ex: `UserNotFoundException`). Caso a senha informada não corresponda à atual, deve retornar uma exceção personalizada (ex: `InvalidPasswordException`);
        - `authenticate`: para simular a autenticação de um usuário. Caso o login ou a senha estejam incorretos, deve retornar uma exceção personalizada (ex: `InvalidLoginException`).
          <br><br>
    * O algoritmo de hash de senha (e.g. **PBKDF2** ou **BCrypt**) deve ser configurável, ou seja, deve ser possível alterar o algoritmo de hash sem modificar o código da aplicação. Assim, ao instanciar a classe `UserService`, deve-se passar o algoritmo de hash a ser utilizado. O algoritmo **BCrypt** deve ser o padrão, mas o usuário deve poder escolher outro algoritmo ao iniciar a aplicação.
      <br><br>
3. O sistema deve ser implementado com o padrão de projeto **Repository**, que separa a lógica de acesso a dados da lógica de negócio.
   <br><br>
    * A interface `UserRepository` deve ter os seguintes métodos:
        - `save`: recebe um objeto do tipo `User` e o armazena na camada de persistência;
        - `update`: recebe um objeto do tipo `User` e atualiza os dados do usuário na camada de persistência;
        - `findByLogin`: recebe um login e retorna o objeto do tipo `User` correspondente ao login informado, ou `null` caso não exista um usuário com o login informado.
          <br><br>
    * A interface `UserRepository` consiste em uma abstração da camada de persistência, ou seja, independente da tecnologia utilizada para armazenar os dados (ex: em memória, em arquivos de texto ou em um banco de dados relacional).

        - Você deverá prover uma implementação para armazenar os dados dos usuários em uma coleção em memória (ex: `List<User>` ou `Map<String, User>`).
        - Opcionalmente, você pode prover outras implementações para armazenar os dados dos usuários em arquivos de texto ou em um banco de dados relacional (ex: H2, SQLite, MySQL, PostgreSQL, etc.).


``` mermaid
classDiagram

    class User {
        - String login
        - String passwordHash
    }

    class HashAlgorithm {
        <<interface>>
        + hash(String password) String
        + verify(String password, String hash) boolean
    }

    class BCryptHashAlgorithm {
        + hash(String password) String
        + boolean verify(String password, String hash) boolean
    }

    class UserService {
        - UserRepository repository
        - HashAlgorithm hashAlgorithm
        
        + register(String login, String password) void
        + updatePassword(String login, String currentPassword, String newPassword) void
        + authenticate(String login, String password) void
    }

    class UserRepository {
        <<interface>>
        + save(User user) void
        + update(User user) void
        + findByLogin(String login) User
    }

    class FileUserRepository {
        - String filePath
        - Map<String, User> users
        
        + save(User user) void
        + update(User user) void
        + findByLogin(String login) User
        
        - scanFile() Scanner
        - newPrintWriter() PrintWriter
        - loadFromFile() void
        - saveToFile() void
    }

    class UserAlreadyExistsException
    class UserNotFoundException
    class InvalidPasswordException
    class InvalidLoginException
    
    UserService --> UserRepository : uses
    UserService --> HashAlgorithm : uses
    UserService --> User : uses
    UserRepository --> User : uses
    
    HashAlgorithm <|.. BCryptHashAlgorithm : implements
    UserRepository <|.. FileUserRepository : implements
    
    UserService --> UserAlreadyExistsException : throws
    UserService --> UserNotFoundException : throws
    UserService --> InvalidPasswordException : throws
    UserService --> InvalidLoginException : throws


```

> [!NOTE]
> O método System.console().readPassword() pode ser utilizado para ler a senha do usuário sem exibi-la na tela. O método readPassword retorna um char[] com a senha informada pelo usuário. Porém, se você estiver executando a aplicação a partir de uma IDE ou com gradle run, não será possível obter um console. Para usar esse método, execute a aplicação da seguinte forma:
> 
> ```bash
> # Para gerar os scripts de execução. Se estiver no windows, use gradlew.bat
> ./gradlew installDist
> 
> # Para executar a aplicação com o script gerado. Se estiver no windows, use app.bat
> ./app/build/install/app/bin/app
