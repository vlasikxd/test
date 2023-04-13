### Summary

Этот микросервис отвечает за авторизацию и аутентификацию и за все операции, которые отвечают за логику и за изменение сущностей данного микросервиса

### С чего начинать

Доступы. Если ты читаешь это, значит доступ к проекту у тебя уже есть
<ol>
<li>в докере подними БД в командной строке запущенной от имени администратора надо ввести <code>docker run --name postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=postgres -p 5434:5432 -d postgres</code>, где --name имя контейнера, -e POSTGRES_PASSWORD= пароль БД,  POSTGRES_USER= логин БД</li>
<li>так же в БД нужно создать схему: authorization</li>
<li>добейся успешного запуска своего микросервиса</li>
</ol>


### Что такое JWT token

JWT (JSON Web Token) — это открытый стандарт (RFC 7519) для создания токенов доступа, которые могут использоваться для аутентификации и авторизации в веб-приложениях. JWT состоит из трех частей: заголовка (header), полезной нагрузки (payload) и подписи (signature).

Более подробно можно почитать в этой [статье](https://struchkov.dev/blog/ru/what-is-jwt/).

Код, который представлен в данном сервисе, реализует класс JwtProvider, который используется для создания и проверки JWT-токенов в Java-приложении. Он предоставляет методы для создания JWT-токена, получения информации о пользователе из JWT-токена, разрешения токена из запроса и проверки подлинности JWT-токена.

С подробным примером реализации JWT можно ознакомиться [тут](https://struchkov.dev/blog/ru/jwt-implementation-in-spring/).

### Механизм аутентификации

* Клиент API, фронт или другой сервис, присылает сервису авторизации JSON с логином и паролем.


* Если пароль подходит, то наш сервис генерирует access и refresh токены и отправляет их в ответ.


* Клиент API использует access токен для взаимодействия с защищенными эндпойнтами.


* Через пять минут, когда у access токена истекает срок службы, клиент отправляет refresh токен и получает новый access токен. И так по кругу, пока не истечёт срок службы refresh токена.


* Refresh токен выдается на 30 дней. Примерно на 25-29 день клиент API отправляет валидный refresh токен вместе с валидным access токеном и взамен получает новую пару токенов.

### Пример работы с JWT

Для получения токенов необходимо выполнить следующие шаги:

1.  В базу в схему auth в таблицу users необходимо добавить пользователя. Для этого запусти docker с базой, которую ты сделал до этого и запусти команду
    <code>
    insert into auth.users (role, profile_id, password, user_name) VALUES ('ADMIN',1,'$2a$10$nyT8XiMY.3sx28IdnCuV7.37nYS0QOhOZ/OGsje0.FfoXUA.lDMZW','admin');
    </code>

    ,где
    
    *   role - 'ADMIN' или 'USER'
    *   profileID - формат BIGINT
    *   password - записывается в формате bcrypt (в данном примере пароль "password" зашифрован bcrypt.) Зашифровать пароль можно [bcrypt-generator](https://bcrypt-generator.com/). Переходим по ссылке, вставляем в строку пароль который хотим зашифровать и нажимаем кнопку Encrypt, получаем зашифрованный пароль, который вставляем в SQL запрос.

    Пользователя также можно добавлять в базу любым удобным способом, например через Intellij IDEA. Главное, чтобы пароль хранился в базе в зашифрованном виде.


2.  Открыть Postman и выбрать запрос для получения токена (обычно это запрос на аутентификацию). В нашем случае выбрать метод POST, URL - http://localhost:8087/api/authorization/auth/login. Выбрать Body поставить формат raw и выбрать тип JSON. В Body написать запрос в формате JSON:
    ```json
        {
        "username":"admin",
        "password":"password"
        }  
    ```

    где,
    
    *   username - имя пользователя в таблице users.
    *   password - пароль пользователя в не зашифрованном виде("password" в нашем примере).
    

3.  Отправить запрос на аутентификацию. Если запрос выполнен успешно, сервер вернет в ответе JSON с токенами.
    ```json
        {
        "type": "Bearer",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MTM5MzkyMiwicm9sZSI6WyJBRE1JTiJdfQ.vS2WIO_pFZhUUHyioQsN6TvnZpp6fF_zSgQXdHBv6NaJuEg9yUXnHIWaqsReE3caguX4q3-7QXm0TrraWhxvwA",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4Mzk4NTYyMn0.NiOxYRiFNbdmeR3hy_7e3qMNfSDulIQK_hIK3Od720jGkUEFHmPkzGg0GCKfPUdPN2DJ9GGChFey65VOBI3Suw"
        }
    ```

4.  Скопировать полученный access токен.

 
5.  Чтобы использовать этот токен в запросах на защищенные эндпоинты, вставьте его в соответствующий заголовок авторизации. Для этого выберите запрос (например метод POST, URL - http://localhost:8087/api/authorization/users), в который нужно добавить заголовок, и перейдите во вкладку "Authorization". В поле "Type" выбрать "Bearer Token" и в поле "Token" вставить скопированное значение access токена, без ковычек (в нашем примере это было бы: <code>eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MTM5MzkyMiwicm9sZSI6WyJBRE1JTiJdfQ.vS2WIO_pFZhUUHyioQsN6TvnZpp6fF_zSgQXdHBv6NaJuEg9yUXnHIWaqsReE3caguX4q3-7QXm0TrraWhxvwA</code> ). Выбрать вкладку "Body" поставить формат raw и выбрать тип JSON. В Body написать запрос в формате JSON например:
    ```json
        {
        "id": 0,
        "username": "user",
        "role": "USER",
        "profileId": 2,
        "password": "userpass"
        }
    ```


6.  Выполните запрос с добавленным заголовком и убедитесь, что он выполняется успешно. Ответ придет в формате JSON. Пользователь создан. В данном примере ответ будет такой:
    ```json
        {
        "id": 2,
        "username": "user",
        "role": "USER",
        "profileId": 2,
        "password": "$2a$10$F/5mrZIJ9n2Nd3Az7FekMetCxiGRrEH8x9SQTPnrPispL8Z.CE/Da"
        }
    ``` 

    И как вы заметили, пароль возвращается уже в зашифрованном виде.


7.  Через 5 минут после получения, срок службы access токена истекает и при его использовании в запросах получим такую ошибку:
    ```json
        {
        "timestamp": "2023-04-13T15:17:59.406+00:00",
        "status": 403,
        "error": "Forbidden",
        "path": "/api/authorization/users"
        }
    ```
8.  Чтобы получить новый access токен отправьте POST запрос на http://localhost:8087/api/authorization/auth/token. В теле запроса указываем наш refresh токен.
    ```json
        {
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4Mzk4NTYyMn0.NiOxYRiFNbdmeR3hy_7e3qMNfSDulIQK_hIK3Od720jGkUEFHmPkzGg0GCKfPUdPN2DJ9GGChFey65VOBI3Suw"
        }
    ``` 
    В ответ придет новый access токен:
    ```json
        {
        "type": "Bearer",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MTQwMDE5Mywicm9sZSI6WyJBRE1JTiJdfQ.XI_g8G6q_A1FVAd1GgD8z-hvRihRCGvOyhh49t5pzqTq9plU39cnsYuvu4EiVefiPyQNQmgxO5ML1qk-P1b7Fg",
        "refreshToken": null
        }
    ```
    

9.  Срок службы refresh токена истекает спустя 30 дней после выдачи. Для замены текущего refresh токена отправьте POST запрос на http://localhost:8087/api/authorization/auth/token в теле запроса передайте текущий refresh токен. Также не стоит забывать, что это защищенный метод, поэтому во вкладке Authorization вставьте access токен. В нашем примере запрос будет выглядеть так:
    ```json
        {
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4Mzk4NTYyMn0.NiOxYRiFNbdmeR3hy_7e3qMNfSDulIQK_hIK3Od720jGkUEFHmPkzGg0GCKfPUdPN2DJ9GGChFey65VOBI3Suw"
        }
    ```
    В ответ придут новые access и refresh токены.
    ```json
        {
        "type": "Bearer",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MTQwMzE2MCwicm9sZSI6WyJBRE1JTiJdfQ.edu_qt6zJzKUbsEISe-C1IaXhJgFVObVx7iFpbZFislk95MmxzdMaK19sJzf3JwkAeTRbEBgat0vOQ5R831UvA",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4Mzk5NDg2MH0.h20WzV415mDXQ6irpIt4gTZUGqBO975NoRsQN19X3TbynL2t3HgED1EEw4zV1PQzlyGdMOz2FAS7JAVR8BNJiA"
        }
    ```
10. Токены перестают быть актуальными при перезапуске приложения. Нужно пройти процедуру получения токена заново.