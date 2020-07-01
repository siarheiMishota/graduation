create table users
(
    id          int auto_increment,
    passport_id varchar(20)                     not null,
    date_birth  date                    not null,
    login       varchar(25)             not null,
    password    varchar(100)             not null,
    email       varchar(40)             not null,
    first_name  varchar(25)             not null,
    surname     varchar(25)             not null,
    father_name varchar(25)             not null,
    gender      enum ('male', 'female') not null,
    confirmed   boolean                default false,
    role        enum ('user', 'admin') default 'user',

    primary key (id),

    constraint email
        unique (email),
    constraint login
        unique (login),
    constraint login
        unique (passport_id)
);

create table entrants
(
    id      int auto_increment,
    user_id int,

    primary key (id)
);

create table faculties
(
    id                 int auto_increment,
    name               varchar(100) not null,
    number_free_places int          not null,
    number_pay_places  int          not null,

    unique (name),

    primary key (id)
);

create table entrants_faculties_priority
(
    id           int auto_increment,
    entrant_id   int not null,
    faculties_id int not null,
    priority     int not null,

    primary key (id),


    foreign key (entrant_id) REFERENCES entrants (id),
    foreign key (faculties_id) REFERENCES faculties (id)
);

create table subjects
(
    id   int auto_increment,
    name varchar(30) not null unique,

    primary key (id)
);

create table subjects_to_faculties
(
    id           int auto_increment,
    faculties_id int not null,
    subject_id   int not null,

    primary key (id),
    foreign key (faculties_id) references faculties (id),
    foreign key (subject_id) references subjects (id)
);

create table subjects_results
(
    id            int auto_increment,
    entrant_id    int not null,
    subject_id    int not null,
    number_points int not null,

    primary key (id),
    foreign key (entrant_id) references entrants (id),
    foreign key (subject_id) references subjects (id)
);

create table students
(
    id         int auto_increment,
    user_id    int     not null,
    faculty_id int     not null,
    budget     boolean not null,

    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (faculty_id) references faculties (id)
);

insert into users(passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed,
                  role)
    value (659, '1996-05-08', 'mishota', 'solo', 'soloyoloswag1@yandex.ru', 'Мишота', 'Сергей', 'Александрович', 'male',
           true, 'admin');

insert into users(passport_id, date_birth, login, password, email, surname, first_name, father_name, gender, confirmed)
values (530, '1961-11-06', 'kryazev', 'passwordkryazev', 'kryazev@gmail.com', 'Кряжев', 'Олег', 'Викторович', 'male',
        true),
       (510, '1961-02-08', 'uzefovich', 'uzefovichPassword', 'uzefovich@gmail.com', 'Юзефович', 'Сергей',
        'Викторович', 'male', true),
       (503, '1971-01-06', 'makarevich', 'makarevichPassword', 'makarevich@gmail.com', 'Макаревич', 'Андрей',
        'Вадимович', 'male', true),
       (531, '1991-2-19', 'prokopovich', 'prokopovichPassword', 'prokopovich@gmail.com', 'Прокопович', 'Виталий',
        'Анатольевич',
        'male', true),
       (509, '1987-12-15', 'yarockui', 'yarockuiPassword', 'yarockui@gmail.com', 'Яроцкий', 'Дмитрий', 'Васильевич',
        'male', true),
       (536, '1978-01-01', 'igumov', 'igumovPassword', 'igumov@gmail.com', 'Игумов', 'Всеволод', 'Сергеевич',
        'male', true),
       (502, '1965-05-01', 'nazarov', 'nazarovPassword', 'nazarov@gmail.com', 'Назаров', 'Анатолий', 'Александрович',
        'male', true),
       (538, '1986-09-08', 'prockui', 'prockuiPassword', 'prockui@gmail.com', 'Процкий', 'Владимир', 'Анатольевич',
        'male',
        true),
       (589, '1961-4-04', 'shashko', 'shashkoPassword', 'shashko@gmail.com', 'Шашко', 'Алексей', 'Петрович',
        'male', true),
       (598, '1966-03-26', 'tarshikova', 'tarshikovaPassword', 'tarshikova@gmail.com', 'Таршикова', 'Тамара',
        'Демьяновна',
        'female', true),
       (548, '1989-5-14', 'ogneva', 'ognevaPassword', 'ogneva@gmail.com', 'Огнева', 'Ольга', 'Владимировна',
        'female', true),
       (454, '1997-4-24', 'sokolchik', 'sokolchikPassword', 'sokolchik@gmail.com', 'Скольчик', 'Татьяна', 'Анатольевна',
        'female', true),
       (652, '1971-4-04', 'shytko', 'shytkoPassword', 'shytko@gmail.com', 'Шутко', 'Галина', 'Павловна',
        'female', true),
       (752, '1998-2-04', 'grechkina', 'grechkinaPassword', 'grechkina@gmail.com', 'гречкина', 'Галина', 'Николаевна',
        'female', true),
       (653, '1972-5-03', 'davidovich', 'davidovichPassword', 'davidovich@gmail.com', 'Давидович', 'Лидия', 'Ивановна',
        'female', true),
       (654, '1973-5-02', 'gorabchik', 'gorabchikPassword', 'gorabchik@gmail.com', 'Грабчик', 'Нина', 'Петровна',
        'female', true),
       (655, '1981-7-01', 'kryglenya', 'kryglenyaPassword', 'kryglenya@gmail.com', 'Кругленя', 'Светлана', 'Николаевна',
        'female', true),
       (656, '1991-5-12', 'sirotina', 'sirotinaPassword', 'sirotina@gmail.com', 'Сиротина', 'Людмила', 'Андреевна',
        'female', true),
       (657, '1999-6-11', 'malinovskaya', 'malinovskayaPassword', 'malinovskaya@gmail.com', 'Малиновская', 'Екатерина',
        'Степановна',
        'female', true);

insert into subjects(name)
VALUES ('maths'),
       ('physics'),
       ('russian language'),
       ('chemistry'),
       ('biology'),
       ('history'),
       ('geography'),
       ('social science'),
       ('english language'),
       ('french language');

insert into faculties (name, number_free_places, number_pay_places)
values ('ФИТР', 5, 5),
       ('АТФ', 2, 3),
       ('ФГДЭ', 4, 3),
       ('МСФ', 6, 6),
       ('МТФ', 2, 3),
       ('ФММП', 6, 4),
       ('ЭФ', 7, 2),
       ('ФТУГ', 10, 0),
       ('ИПФ', 0, 12),
       ('ФЭС', 4, 4),
       ('АС', 3, 3),
       ('СФ', 2, 2),
       ('ПСФ', 5, 2)
;

insert into students (user_id, faculty_id, budget)
values (1, 43, true),
       (2, 44, true),
       (3, 45, false),
       (4, 46, false),
       (5, 47, true),
       (6, 48, false),
       (7, 49, true),
       (8, 50, false),
       (9, 51, false),
       (10, 52, true)
;

insert into subjects_to_faculties (faculties_id, subject_id)
values (43, 1),
       (43, 2),
       (43, 3),
       (44, 4),
       (44, 3),
       (44, 2);