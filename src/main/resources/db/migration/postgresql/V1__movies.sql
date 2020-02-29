DROP TABLE IF EXISTS actors;
CREATE TABLE actors (
  id             BIGSERIAL PRIMARY KEY,
  first_name     VARCHAR(32),
  last_name      VARCHAR(32)
);


DROP TABLE IF EXISTS movies_1;
CREATE TABLE movies_1 (
  id             BIGSERIAL PRIMARY KEY,
  title          VARCHAR(64),
  summary        VARCHAR(512),
  released_at    DATE,
  roles          VARCHAR,
  tags           VARCHAR(64) ARRAY
);


DROP TABLE IF EXISTS movies_2;
CREATE TABLE movies_2 (
  id             BIGSERIAL PRIMARY KEY,
  title          VARCHAR(64),
  summary        VARCHAR(512),
  released_at    DATE,
  tags           VARCHAR(64) ARRAY
);

DROP TABLE IF EXISTS casting;
CREATE TABLE roles (
  id             BIGSERIAL PRIMARY KEY,
  movie          BIGINT REFERENCES movies_2(id),
  actor          BIGINT,--REFERENCES actors(id),
  name           VARCHAR(64),
  UNIQUE (movie, actor, name)
);