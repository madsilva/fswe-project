# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table candidate (
  firstname                     varchar(255),
  lastname                      varchar(255),
  party                         varchar(255)
);

create table login_data (
  username                      varchar(255) not null,
  password                      varchar(255),
  firstname                     varchar(255),
  lastname                      varchar(255),
  privilege                     varchar(255),
  constraint pk_login_data primary key (username)
);

create table user_id (
  username                      varchar(255),
  password                      varchar(255),
  conf_password                 varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255)
);

create table voter_registration (
  username                      varchar(255),
  address                       varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip_code                      varchar(255),
  date_of_birth                 varchar(255),
  social_security               varchar(255),
  id_number                     varchar(255),
  approved                      tinyint(1) default 0 not null
);


# --- !Downs

drop table if exists candidate;

drop table if exists login_data;

drop table if exists user_id;

drop table if exists voter_registration;

