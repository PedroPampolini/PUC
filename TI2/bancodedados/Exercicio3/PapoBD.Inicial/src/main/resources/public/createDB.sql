DROP TABLE IF EXISTS aluno;

CREATE TABLE aluno (
    id serial PRIMARY KEY,
    nome VARCHAR(64),
    email VARCHAR(64),
    senha VARCHAR(16),
    idade INTEGER
);

INSERT INTO aluno (id, nome, email, senha, idade) VALUES (1, 'Aluno Um','aluno1@email.com','adminAluno1',15);
INSERT INTO aluno (id, nome, email, senha, idade) VALUES (2, 'Aluno Dois','aluno2@email.com','adminAluno2',15);
