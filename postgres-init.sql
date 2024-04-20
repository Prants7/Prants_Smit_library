create user library_owner with encrypted password 'library_owner';
create database prants_library owner library_owner;
\c prants_library
create schema library_schema;
revoke all on schema public from public;
grant usage on schema public to library_owner;

create role library_user_role;
grant connect on database prants_library to library_user_role;
grant usage on schema library_schema to library_user_role;

create user library_user with encrypted password 'library_user';
grant library_user_role to library_user;
