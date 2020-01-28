DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(
    ID BIGINT PRIMARY KEY,
    TITLE VARCHAR(255),
    AUTHOR_ID BIGINT,
    GENRE_ID BIGINT
);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(
    ID BIGINT PRIMARY KEY,
    NAME VARCHAR(255),
    SURNAME VARCHAR(255),
    BOOK_ID BIGINT
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(
    ID BIGINT PRIMARY KEY,
    NAME VARCHAR(255)
);

ALTER TABLE BOOKS
ADD FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID) ON DELETE CASCADE;

ALTER TABLE BOOKS
ADD FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID) ON DELETE CASCADE;

ALTER TABLE AUTHORS
ADD FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID) ON DELETE CASCADE;