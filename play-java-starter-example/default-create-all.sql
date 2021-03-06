create table ballots (
  precinct                      varchar(255) not null,
  election_id                   varchar(255),
  constraint pk_ballots primary key (precinct)
);

create table candidate (
  firstname                     varchar(255),
  lastname                      varchar(255),
  party                         varchar(255),
  precinct                      varchar(255),
  election_id                   varchar(255),
  position                      varchar(255)
);

create table election (
  election_id                   varchar(255) not null,
  election_type                 varchar(255),
  state                         varchar(255),
  start_date                    datetime(6),
  end_date                      datetime(6),
  constraint pk_election primary key (election_id)
);

create table login_data (
  username                      varchar(255) not null,
  password                      varchar(255),
  conf_password                 varchar(255),
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

create table search (
  sql_column                    varchar(255) not null,
  criteria                      varchar(255),
  constraint pk_search primary key (sql_column)
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

