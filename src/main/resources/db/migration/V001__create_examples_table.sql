create table EXAMPLES
(
    id    SERIAL,
    name  VARCHAR(100) NOT NULL,
    value numeric      NOT NULL,
    CONSTRAINT examples_uk1 UNIQUE (name, value),
    primary key (id)
);
