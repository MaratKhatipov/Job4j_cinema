create table tickets
(
    id         serial primary key,
    session_id int not null references sessions (id),
    pos_row    int not null,
    cell       int not null,
    user_id    int not null references users (id)
);