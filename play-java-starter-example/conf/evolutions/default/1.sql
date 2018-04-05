# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table candidate (
  firstname                     varchar(255),
  lastname                      varchar(255),
  party                         varchar(255),
  precinct                      varchar(255)
);

create table election (
  election_type                  varchar(255),
  state                         varchar(255),
  election_id                    varchar(255)
);

create table login_data (
  username                      varchar(255) not null,
  password                      varchar(255),
  firstname                     varchar(255),
  lastname                      varchar(255),
  priviledge                    varchar(255),
  reset_token                   varchar(255),
  constraint pk_login_data primary key (username)
);

create table precinct (
  zip                           varchar(255) not null,
  precinct_id                   varchar(255),
  constraint pk_precinct primary key (zip)
);

create table state_geography (
  state                         varchar(255),
  zip                           varchar(255),
  county                        varchar(255),
  city                          varchar(255)
);

create table user_id (
  username                      varchar(255),
  password                      varchar(255),
  conf_password                 varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255)
);

create table voter_registration (
  username                      varchar(255) not null,
  address                       varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip_code                      varchar(255),
  date_of_birth                 varchar(255),
  social_security               varchar(255),
  id_number                     varchar(255),
  approved                      tinyint(1) default 0 not null,
  constraint pk_voter_registration primary key (username)
);


# --- !Downs

drop table if exists candidate;

drop table if exists election;

drop table if exists login_data;

drop table if exists precinct;

drop table if exists state_geography;

drop table if exists user_id;

drop table if exists voter_registration;

