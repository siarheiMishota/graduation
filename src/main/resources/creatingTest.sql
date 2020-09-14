# create schema test;
create table users
(
    id              int auto_increment,
    passport_id     varchar(20)             not null,
    date_birth      date                    not null,
    login           varchar(25)             not null,
    password        varchar(100)            not null,
    email           varchar(40)             not null,
    first_name      varchar(25)             not null,
    surname         varchar(25)             not null,
    father_name     varchar(25)             not null,
    gender          enum ('male', 'female') not null,
    confirmed       boolean                default false,
    role            enum ('user', 'admin') default 'user',
    activation_code varchar(40),

    primary key (id),

    constraint email
        unique (email),
    constraint login
        unique (login),
    constraint passport_id
        unique (passport_id)
);

create table entrants
(
    id          int auto_increment,
    user_id     int unique,
    certificate int not null,
    date        datetime not null default now(),

    primary key (id),
    foreign key (user_id) references users (id) ON DELETE CASCADE
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
    id         int auto_increment,
    entrant_id int not null,
    faculty_id int not null,
    priority   int not null,

    primary key (id),
    unique (entrant_id, faculty_id),


    foreign key (entrant_id) REFERENCES entrants (id) ON DELETE CASCADE,
    foreign key (faculty_id) REFERENCES faculties (id) ON DELETE CASCADE
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
    foreign key (faculties_id) references faculties (id) ON DELETE CASCADE,
    foreign key (subject_id) references subjects (id) ON DELETE CASCADE
);

create table subjects_results
(
    id         int auto_increment,
    entrant_id int not null,
    subject_id int not null,
    points     int not null,

    unique (entrant_id, subject_id),
    primary key (id),
    foreign key (entrant_id) references entrants (id) ON DELETE CASCADE,
    foreign key (subject_id) references subjects (id) ON DELETE CASCADE
);

create table students
(
    id         int auto_increment,
    user_id    int     not null,
    faculty_id int     not null,
    budget     boolean not null,

    unique (user_id),
    primary key (id),
    foreign key (user_id) references users (id) ON DELETE CASCADE,
    foreign key (faculty_id) references faculties (id) ON DELETE CASCADE
);

create table photos
(
    id      int auto_increment,
    user_id int         not null,
    name    varchar(50) not null,

    UNIQUE (name),
    primary key (id),
    foreign key (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table news
(
    id                   int auto_increment,
    name_ru              varchar(200)  not null,
    name_en              varchar(200)  not null,
    user_creator_id      int           not null,
    brief_description_ru varchar(2000) not null,
    brief_description_en varchar(2000) not null,
    english_variable     text          not null,
    russian_variable     text          not null,
    creation_date        datetime      not null,

    primary key (id),
    foreign key (user_creator_id) references users (id) on DELETE CASCADE
);

insert into users(passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed,
                  role)
    value (659, '1996-05-08', 'mishota', '5653c6b1f51852a6351ec69c8452abc6', 'soloyoloswag1@yandex.ru', 'Мишота',
           'Сергей', 'Александрович', 'male',
           true, 'admin');

insert into users(passport_id, date_birth, login, password, email, surname, first_name, father_name, gender, confirmed)
values (530, '1961-11-06', 'kryazev', '1c5ecc80c46574295ed152b851a59276', 'kryazev@gmail.com', 'Кряжев', 'Олег',
        'Викторович', 'male',
        true),
       (510, '1961-02-08', 'uzefovich', '4e915411d4842e489243ce0b8501d4e', 'uzefovich@gmail.com', 'Юзефович', 'Сергей',
        'Викторович', 'male', true),
       (503, '1971-01-06', 'makarevich', 'f66c993f65d6bcdc1e6e763cb6ea2aa', 'makarevich@gmail.com', 'Макаревич',
        'Андрей',
        'Вадимович', 'male', true),
       (531, '1991-2-19', 'prokopovich', 'aef8c99d064df73d7594d531e181c1', 'prokopovich@gmail.com', 'Прокопович',
        'Виталий',
        'Анатольевич',
        'male', true),
       (509, '1987-12-15', 'yarockui', '77ae1bb9193fe79ee4a74c75a3b2621', 'yarockui@gmail.com', 'Яроцкий', 'Дмитрий',
        'Васильевич',
        'male', true),
       (536, '1978-01-01', 'igumov', 'ec82c5fd39ee9d02a49768e873ec74', 'igumov@gmail.com', 'Игумов', 'Всеволод',
        'Сергеевич',
        'male', true),
       (502, '1965-05-01', 'nazarov', '9241c56540cf4c6c1fbc988739997a4a', 'nazarov@gmail.com', 'Назаров', 'Анатолий',
        'Александрович',
        'male', true),
       (538, '1986-09-08', 'prockui', '5e7bedc9811c44c1024c399ef68748', 'prockui@gmail.com', 'Процкий', 'Владимир',
        'Анатольевич',
        'male',
        true),
       (589, '1961-4-04', 'shashko', 'ecd770dea444b6ffff80a6cbefe133a', 'shashko@gmail.com', 'Шашко', 'Алексей',
        'Петрович',
        'male', true),
       (598, '2004-03-26', 'tarshikova', '3f55382691a4f3b734c36133deee612', 'tarshikova@gmail.com', 'Таршикова',
        'Тамара',
        'Демьяновна',
        'female', true),
       (548, '1989-5-14', 'ogneva', '249392d9b14f3d9ea22aaaf49597365', 'ogneva@gmail.com', 'Огнева', 'Ольга',
        'Владимировна',
        'female', true),
       (454, '1997-4-24', 'sokolchik', 'd3d0d5886536f5235e9ec96dd29f8', 'sokolchik@gmail.com', 'Скольчик', 'Татьяна',
        'Анатольевна',
        'female', true),
       (652, '1971-4-04', 'shytko', '92cf9a5282cbb4465afe8137ae6731a1', 'shytko@gmail.com', 'Шутко', 'Галина',
        'Павловна',
        'female', true),
       (752, '1998-2-04', 'grechkina', '419bbaee5081951fcdc94448164ff17', 'grechkina@gmail.com', 'гречкина', 'Галина',
        'Николаевна',
        'female', true),
       (653, '1972-5-03', 'davidovich', '213756933db5de22fe234b6177ff664', 'davidovich@gmail.com', 'Давидович', 'Лидия',
        'Ивановна',
        'female', true),
       (654, '1973-5-02', 'gorabchik', 'c460fbe5f2262ee983fb3841c8871759', 'gorabchik@gmail.com', 'Грабчик', 'Нина',
        'Петровна',
        'female', true),
       (655, '1981-7-01', 'kryglenya', 'b8dddf165c27a5818706ffe5457fc36', 'kryglenya@gmail.com', 'Кругленя', 'Светлана',
        'Николаевна',
        'female', true),
       (656, '1991-5-12', 'sirotina', '8512d91535b7b6a96e24c0444ba64a', 'sirotina@gmail.com', 'Сиротина', 'Людмила',
        'Андреевна',
        'female', true),
       (657, '1999-6-11', 'malinovskaya', '98586f8a59ed3e08b81e360e5991035', 'malinovskaya@gmail.com', 'Малиновская',
        'Екатерина',
        'Степановна',
        'female', true);

insert into subjects(name)
VALUES ('maths'),
       ('physics'),
       ('russian.language'),
       ('chemistry'),
       ('biology'),
       ('history'),
       ('geography'),
       ('social.science'),
       ('english.language'),
       ('french.language');

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


insert into subjects_to_faculties (faculties_id, subject_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3),
       (5, 1),
       (5, 2),
       (5, 3),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 3),
       (7, 4),
       (7, 5),
       (8, 5),
       (8, 6),
       (8, 7),
       (9, 6),
       (9, 7),
       (9, 8),
       (10, 7),
       (10, 8),
       (10, 9),
       (11, 10),
       (11, 1),
       (11, 2),
       (12, 1),
       (12, 2),
       (12, 3),
       (13, 2),
       (13, 3),
       (13, 4);

insert into entrants (user_id, certificate,date)
values (1, 10,now()),
       (2, 20,now()),
       (3, 30,now()),
       (4, 40,now()),
       (5, 50,now()),
       (6, 60,now()),
       (7, 70,now()),
       (8, 80,now()),
       (9, 90,now()),
       (10, 25,now()),
       (11, 10,now()),
       (12, 30,now()),
       (13, 53,now()),
       (14, 88,now()),
       (15, 25,now()),
       (16, 30,now()),
       (17, 35,now()),
       (18, 40,now()),
       (19, 45,now());


insert into subjects_results (entrant_id, subject_id, points)
values (1, 1, 10),
       (1, 2, 20),
       (1, 3, 100),
       (2, 1, 19),
       (2, 2, 29),
       (2, 3, 100),
       (3, 2, 9),
       (3, 3, 39),
       (3, 4, 10),
       (4, 1, 19),
       (4, 2, 43),
       (4, 3, 50),
       (5, 1, 9),
       (5, 2, 39),
       (5, 3, 10),
       (6, 1, 9),
       (6, 2, 39),
       (6, 3, 10),
       (7, 1, 9),
       (7, 2, 39),
       (7, 3, 10),
       (8, 3, 9),
       (8, 4, 39),
       (8, 5, 100),
       (9, 5, 9),
       (9, 6, 39),
       (9, 7, 60),
       (10, 6, 9),
       (10, 7, 39),
       (10, 8, 60),
       (11, 7, 9),
       (11, 8, 39),
       (11, 9, 60),
       (12, 10, 9),
       (12, 1, 39),
       (12, 2, 60),
       (13, 1, 9),
       (13, 2, 39),
       (13, 3, 60),
       (14, 2, 9),
       (14, 3, 39),
       (14, 4, 60),
       (15, 1, 9),
       (15, 2, 39),
       (15, 3, 60),
       (16, 2, 9),
       (16, 3, 39),
       (16, 4, 60),
       (17, 5, 9),
       (17, 6, 39),
       (17, 7, 60),
       (18, 6, 9),
       (18, 7, 39),
       (18, 8, 60),
       (19, 7, 9),
       (19, 8, 39),
       (19, 9, 60);

insert into entrants_faculties_priority(entrant_id, faculty_id, priority)
VALUES (1, 1, 1),
       (1, 3, 2),
       (1, 4, 3),
       (1, 5, 4),
       (1, 6, 5),
       (1, 12, 6),
       (2, 1, 1),
       (2, 3, 2),
       (2, 4, 3),
       (2, 5, 4),
       (2, 6, 5),
       (2, 12, 6),
       (3, 2, 1),
       (3, 13, 2),
       (4, 1, 1),
       (4, 3, 2),
       (4, 4, 3),
       (4, 5, 4),
       (4, 6, 5),
       (4, 12, 6),
       (5, 1, 1),
       (5, 3, 2),
       (5, 4, 3),
       (5, 5, 4),
       (5, 6, 5),
       (5, 12, 6),
       (6, 1, 1),
       (6, 3, 2),
       (6, 4, 3),
       (6, 5, 4),
       (6, 6, 5),
       (6, 12, 6),
       (7, 1, 1),
       (7, 3, 2),
       (7, 4, 3),
       (7, 5, 4),
       (7, 6, 5),
       (7, 12, 6),
       (8, 7, 1),
       (9, 8, 1),
       (10, 9, 1),
       (11, 10, 1),
       (12, 11, 1),
       (13, 1, 1),
       (13, 3, 2),
       (13, 4, 3),
       (13, 5, 4),
       (13, 6, 5),
       (13, 12, 6),
       (14, 2, 1),
       (14, 13, 2),
       (15, 1, 1),
       (15, 3, 2),
       (15, 4, 3),
       (15, 5, 4),
       (15, 6, 5),
       (15, 12, 6),
       (16, 2, 1),
       (16, 13, 2),
       (17, 8, 1),
       (18, 9, 1),
       (19, 10, 1);


insert into students (user_id, faculty_id, budget)
values (1, 1, true),
       (2, 2, true),
       (3, 3, true),
       (4, 4, true),
       (5, 5, false),
       (6, 6, false),
       (7, 7, false),
       (8, 8, false),
       (9, 9, false),
       (10, 10, false),
       (11, 11, false),
       (12, 12, false),
       (13, 13, false),
       (14, 1, true),
       (15, 1, true),
       (16, 3, true),
       (17, 4, true),
       (18, 5, true),
       (19, 6, true);

insert into photos (user_id, name)
values (1, 'aaaa'),
       (2, 'bbbb'),
       (3, 'cccc'),
       (4, 'dddd'),
       (5, 'eeee'),
       (6, 'ffff'),
       (7, 'gggg'),
       (8, 'hhhh'),
       (9, 'qqqq'),
       (10, 'wwww'),
       (11, 'rrrr'),
       (12, 'tttt'),
       (13, 'yyyy'),
       (14, 'uuuu'),
       (15, 'iiii'),
       (16, 'oooo')
;

insert into news(name_ru, name_en, user_creator_id, brief_description_ru, brief_description_en, english_variable,
                 russian_variable, creation_date)
VALUES ('name_ru_1','name_en_1', 1, 'brief_description_ru_1','brief_description_en_1', 'english_variable_1', 'russian_variable_10', '2020-08-01 10:19:17'),
       ('name_ru_2','name_en_2', 2, 'brief_description_ru_2','brief_description_en_2', 'english_variable_2', 'russian_variable_2', '2020-08-02 10:19:17'),
       ('name_ru_3','name_en_3', 3, 'brief_description_ru_3','brief_description_en_3', 'english_variable_3', 'russian_variable_3', '2020-08-03 10:19:17'),
       ('name_ru_4','name_en_4', 4, 'brief_description_ru_4','brief_description_en_4', 'english_variable_4', 'russian_variable_4', '2020-08-04 10:19:17'),
       ('name_ru_5','name_en_5', 5, 'brief_description_ru_5','brief_description_en_5', 'english_variable_5', 'russian_variable_5', '2020-08-05 10:19:17'),
       ('name_ru_6','name_en_6', 6, 'brief_description_ru_6','brief_description_en_6', 'english_variable_6', 'russian_variable_6', '2020-08-06 10:19:17'),
       ('name_ru_7','name_en_7', 7, 'brief_description_ru_7','brief_description_en_7', 'english_variable_7', 'russian_variable_7', '2020-08-07 10:19:17'),
       ('name_ru_8','name_en_8', 8, 'brief_description_ru_8','brief_description_en_8', 'english_variable_8', 'russian_variable_8', '2020-08-08 10:19:17'),
       ('name_ru_9','name_en_9', 9, 'brief_description_ru_9','brief_description_en_9', 'english_variable_9', 'russian_variable_9', '2020-08-09 10:19:17'),
       ('name_ru_10','name_en_10', 10, 'brief_description_ru_10','brief_description_en_10', 'english_variable_10', 'russian_variable_10', '2020-08-10 10:19:17'),
       ('name_ru_11','name_en_11', 11, 'brief_description_ru_11','brief_description_en_11', 'english_variable_11', 'russian_variable_11', '2020-08-11 10:19:17'),
       ('name_ru_12','name_en_12', 12, 'brief_description_ru_12','brief_description_en_12', 'english_variable_12', 'russian_variable_12', '2020-08-12 10:19:17'),
       ('name_ru_13','name_en_13', 13, 'brief_description_ru_13','brief_description_en_13', 'english_variable_13', 'russian_variable_13', '2020-08-01 10:19:17'),
       ('name_ru_14','name_en_14', 14, 'brief_description_ru_14','brief_description_en_14', 'english_variable_14', 'russian_variable_14', '2020-08-02 10:19:17')
;