CREATE TABLE ig_account_classify_result (
  id                        varchar(100)  NOT NULL,
  ig_pk                     varchar(50)   NOT NULL,
  examiner                  varchar(100)  NOT NULL,
  result                    varchar(200)  NOT NULL,

  CONSTRAINT id             PRIMARY KEY (id)
);
