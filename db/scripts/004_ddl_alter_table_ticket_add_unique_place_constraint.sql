alter table tickets
add constraint constraint_ticket UNIQUE (session_id, pos_row, cell )