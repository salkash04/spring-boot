insert into users (id, age, email, first_name, last_name, password, username) values (1, 23, 'vaazhen@mail.ru', 'evgeny', 'vasilenkov', '$2a$12$dQBmrFbKvRfLbDW3rWnAcu3W7GhWeA57UAXV6dxviQ9azeTdcxH52', 'admin');
insert into roles (id, rolename) values (1, 'ROLE_ADMIN');
insert into users_roles (user_id, role_id) values (1, 1);
insert into users (id, age, email, first_name, last_name, password, username) values (2, 24, 'vaazhen@mail.re', 'evgeny1', 'vasilenkov1', '$2a$12$dQBmrFbKvRfLbDW3rWnAcu3W7GhWeA57UAXV6dxviQ9azeTdcxH52', 'user');
insert into roles (id, rolename) values (2, 'ROLE_USER');
insert into users_roles (user_id, role_id) values (2, 2);
