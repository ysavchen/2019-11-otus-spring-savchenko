drop table if exists books;
create table books (
    id bigint primary key auto_increment,
    title varchar(255) not null,
    author_id bigint,
    genre_id bigint
);

drop table if exists authors;
create table authors (
    id bigint primary key auto_increment,
    name varchar(255) not null,
    surname varchar(255) not null
);

drop table if exists genres;
create table genres (
    id bigint primary key auto_increment,
    name varchar(255) not null
);

drop table if exists users;
create table users (
    id bigint primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);

alter table books
add foreign key (author_id) references authors(id) on delete cascade;

alter table books
add foreign key (genre_id) references genres(id) on delete cascade;