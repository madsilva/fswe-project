# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table login_data (
  username                      varchar(255) not null,
  password                      varchar(255),
  constraint pk_login_data primary key (username)
);

create table voter_registration (
  username                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  address                       varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  date_of_birth                 varchar(255),
  ssn                           varchar(255),
  id_number                     varchar(255)
);


# --- !Downs

drop table if exists login_data;

drop table if exists voter_registration;

