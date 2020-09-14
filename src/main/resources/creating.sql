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
    certificate int      not null,
    date        datetime not null default now(),

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

