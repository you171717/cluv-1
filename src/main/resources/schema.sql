-- DROP TABLE IF EXISTS mybatis;
-- DROP TABLE IF EXISTS mybatis2;

DROP TABLE IF EXISTS member;
CREATE TABLE member
(
--     id       INT PRIMARY KEY DEFAULT AUTO_INCREMENT,
    member_id INT PRIMARY KEY,
    name      VARCHAR(200),
    email     VARCHAR(200) UNIQUE,
    password  VARCHAR(200),
    address   VARCHAR(300),
    role      VARCHAR(20)
);



-- CREATE TABLE mybatis
-- (
--     id    INT PRIMARY KEY,
--     email VARCHAR(100)
-- );
--
CREATE TABLE mybatis2
(
    id    INT PRIMARY KEY,
    email VARCHAR(100)
);

