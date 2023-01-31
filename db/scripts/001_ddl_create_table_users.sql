CREATE TABLE users
(
    id       serial primary key,
    username varchar not null,
    email    varchar not null unique,
    phone    varchar not null unique,
    password varchar not null
);