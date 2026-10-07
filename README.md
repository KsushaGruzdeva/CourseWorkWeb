# CourseWorkWeb

Многослойное веб-приложение для салона красоты на Java / Spring Boot.

## О проекте

MVP: слой доступа к данным, сервисный слой, контроллеры, пользовательский интерфейс на Thymeleaf. Кэширование через Redis, логирование через ELK, авторизация через Spring Security.

## Стек

- Java
- Spring Boot, Spring Data JPA, Spring Security
- Hibernate ORM
- PostgreSQL
- Redis (кэширование)
- ELK (Elasticsearch, Logstash, Kibana)
- Thymeleaf
- HTML, CSS
- Maven

## Что реализовано

- Многослойная архитектура: репозитории → сервисы → контроллеры → отображения
- Кастомный BaseRepository через EntityManager (вместо JpaRepository)
- DTO, ModelMapper, contract-first design
- Spring Security: роли ADMIN / MASTER / USER, BCrypt, UserDetailsService
- Кэширование через Redis: @Cacheable, @CacheEvict
- Логирование через ELK (Kibana)
- CRUD для услуг, мастеров, записей, отзывов
- Валидация форм, обработка ошибок

## Запуск

```bash
mvn spring-boot:run
```

Приложение доступно на http://localhost:8080.
