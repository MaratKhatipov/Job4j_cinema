create table ticket
(
    id         serial primary key,
    session_id int not null references session (id),
    pos_row    int not null,
    cell       int not null,
    user_id    int not null references users (id)
);