-- 프로젝트 관리를 위해서 sql 문 따로 저장
drop table if exists member CASCADE;
create table member
(
 id bigint generated by default as identity,
 name varchar(255),
 primary key (id)
);