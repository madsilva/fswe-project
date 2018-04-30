# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ballots (
  senator                       varchar(255),
  us_representative             varchar(255),
  mayor                         varchar(255),
  governor                      varchar(255),
  president                     varchar(255)
);

create table candidate (
  candidate_id                  integer auto_increment not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  party                         varchar(255),
  precinct                      varchar(255),
  election_id                   varchar(255),
  position                      varchar(255),
  votes                         integer not null,
  constraint pk_candidate primary key (candidate_id)
);

create table election (
  election_id                   varchar(255) not null,
  election_type                 varchar(255),
  state                         varchar(255),
  start_date                    datetime(6),
  end_date                      datetime(6),
  precinct_id                   varchar(255),
  constraint pk_election primary key (election_id)
);

create table election_results (
  election_id                   varchar(255),
  precinct                      varchar(255),
  candidate                     varchar(255),
  votes                         integer not null
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
  state                         varchar(255),
  constraint pk_precinct primary key (zip)
);

create table search (
  sql_column                    varchar(255) not null,
  criteria                      varchar(255),
  constraint pk_search primary key (sql_column)
);

create table security_questions (
  username                      varchar(255) not null,
  pet                           varchar(255),
  city                          varchar(255),
  school                        varchar(255),
  constraint pk_security_questions primary key (username)
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
  last_name                     varchar(255),
  priviledge                    varchar(255)
);

create table voter_registration (
  username                      varchar(255) not null,
  address                       varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip_code                      varchar(255),
  date_of_birth                 varchar(255),
  gender                        varchar(255),
  party                         varchar(255),
  social_security               varchar(255),
  id_number                     varchar(255),
  approved                      tinyint(1) default 0 not null,
  elections_voted_in            varchar(255),
  constraint pk_voter_registration primary key (username)
);

create table voter_verification (
  username                      varchar(255) not null,
  zip_code                      varchar(255),
  date_of_birth                 varchar(255),
  id_number                     varchar(255),
  constraint pk_voter_verification primary key (username)
);


# --- !Downs

drop table if exists ballots;

drop table if exists candidate;

drop table if exists election;

drop table if exists election_results;

drop table if exists login_data;

drop table if exists precinct;

drop table if exists search;

drop table if exists security_questions;

drop table if exists state_geography;

drop table if exists user_id;

drop table if exists voter_registration;

drop table if exists voter_verification;

