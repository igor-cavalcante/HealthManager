-- Criando o banco de dados
CREATE DATABASE clinica;

-- Conectando ao banco de dados
\c clinica;

-- Criando a tabela medico
CREATE TABLE pessoa (
                        id SERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        telefone VARCHAR(15),
                        crm VARCHAR(20),
                        tipo VARCHAR(50) NOT NULL
);

CREATE TABLE consulta (
                          id_consulta SERIAL PRIMARY KEY,
                          data DATE NOT NULL,
                          valor NUMERIC(10, 2) NOT NULL,
                          observacao TEXT,
                          id_paciente INTEGER NOT NULL,
                          id_medico INTEGER NOT NULL,
                          FOREIGN KEY (id_paciente) REFERENCES pessoa(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_medico) REFERENCES pessoa(id) ON DELETE CASCADE
);


INSERT INTO pessoa (nome, telefone, crm, tipo)
VALUES
    ('Dr. João da Silva', '99999-1234', 'CRM12345', 'MEDICO'),
    ('Dra. Maria Souza', '98888-5678', 'CRM67890', 'MEDICO'),
    ('Ana Costa', '97777-1122', NULL, 'PACIENTE'),
    ('Carlos Mendes', '96666-3344', NULL, 'PACIENTE');

INSERT INTO consulta (data, valor, observacao, id_paciente, id_medico)
VALUES
    ('2024-12-20', 250.00, 'Consulta de rotina', 3, 1),
    ('2024-12-21', 300.00, 'Retorno pós-cirurgia', 4, 2),
    ('2024-12-22', 200.00, 'Avaliação inicial', 3, 2);

