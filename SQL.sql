CREATE DATABASE community;
use community;
-- 유저 테이블
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

alter table users AUTO_INCREMENT = 1;

use community;
select * from users;