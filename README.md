### Кинотеатр

#### В этом проекте используются такие технологии как:

1. Spring boot 2.5.2
2. Thymeleaf 3.0.15
3. Bootstrap 4
4. JDBC
5. PostgreSQL 14
6. Java 17

#### для сборки проекта понадобятся:
1. java 17
2. Maven 3.8.5
3. PostgresSQL 15

#### Запуск проекта:
1. Загрузите проект на свой компьютер.
2. В PostgreSQL создайте базу данных с названием cinema.
3. В корневой папке проекта выполните команду
```shell
mvn install
```

4. Для запуска проекта используйе команду 
 ```shell
java -jar target/job4j_cinema-1.0.jar
```
5. В браузере перейдите по ссылке http://localhost:8080/sessions

страница авторизации

<a href="https://ibb.co/G5RWCcH"><img src="https://i.ibb.co/BCByTqn/image.png" alt="image" border="0"></a>

необходимо зарегистрироваться

<a href="https://ibb.co/x1tp9Th"><img src="https://i.ibb.co/K2pM4v6/image.png" alt="image" border="0"></a>

и авторизоваться

Окно приветствия

<a href="https://ibb.co/hDW78tk"><img src="https://i.ibb.co/2gtnKfw/image.png" alt="image" border="0"></a>

нажимаем на ссылку для выбора сеансов

<a href="https://ibb.co/L1SC6DQ"><img src="https://i.ibb.co/KW697cN/image.png" alt="image" border="0"></a>

При переходе по ссылке с выбранным фильмом попадоем на страницу выбора ряда:
<a href="https://ibb.co/RjNyXBB"><img src="https://i.ibb.co/fD9v3nn/image.png" alt="image" border="0"></a>

Далее выбираем место (если оно занят, то в списке его не будет):

<a href="https://ibb.co/dtJz8Mc"><img src="https://i.ibb.co/kJ6WNBy/image.png" alt="image" border="0"></a>

После выбора отображается инфо о билете(покупке):

<a href="https://ibb.co/1r9LnL4"><img src="https://i.ibb.co/dPQrcrH/image.png" alt="image" border="0"></a>

При успешной операции покупки видим инфо об успешной покупке:

<a href="https://ibb.co/xLGQbW1"><img src="https://i.ibb.co/6wb5SzH/image.png" alt="image" border="0"></a>

Пользователь может не купить билет, потому что его купил другой пользователь.
То есть одновременно выбрали одинаковые места.
В этом случае пользователю выводится информационное сообщение о том, что место куплено.

<a href="https://ibb.co/d48fJ8K"><img src="https://i.ibb.co/Df01g0K/image.png" alt="image" border="0"></a>

## FEEDBACK
почта для обратной связи mmmarat86@gmail.com <br>
телега https://t.me/KhatipovMarat