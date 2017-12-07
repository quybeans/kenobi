CREATE TABLE users (
  id                        varchar(100)  NOT NULL,
  username                  varchar(100)  NOT NULL,
  password_hash             varchar(100)  NOT NULL,
  role                      smallint      NOT NULL,
  password_change_required  boolean       NOT NULL,

  CONSTRAINT pk_id        PRIMARY KEY (id),
  CONSTRAINT uq_username  UNIQUE      (username)
);

INSERT INTO users
  (id, username, password_hash, role, password_change_required)
VALUES
  ('usree3zxo9jxgxly', 'admin', 'admin', 1, true),
  ('usr0j8mefbj9x2kp', 'test', 'test', 0, true);
