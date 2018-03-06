# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table login_data (
  username                      varchar(255) not null,
  password                      varchar(255),
  constraint pk_login_data primary key (username)
);


# --- !Downs

drop table if exists login_data;

