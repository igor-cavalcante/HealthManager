-- Criando o banco de dados
CREATE DATABASE clinica;

-- Conectando ao banco de dados
\c clinica;

-- Criando a tabela pessoa
CREATE TABLE pessoa (
                        id SERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        tipo VARCHAR(50) NOT NULL, -- Discriminador para Médico ou Paciente
                        crm VARCHAR(20) UNIQUE,    -- Campo exclusivo para médicos
                        telefone VARCHAR(15)       -- Campo exclusivo para pacientes
);
CREATE TABLE disponibilidade (
                                 id SERIAL PRIMARY KEY,
                                 id_medico INTEGER NOT NULL,
                                 data DATE NOT NULL,
                                 hora_inicio TIME NOT NULL,
                                 hora_fim TIME NOT NULL,
                                 status VARCHAR(20) DEFAULT 'DISPONIVEL' CHECK (status IN ('DISPONIVEL', 'AGENDADO', 'CANCELADO')),
                                 FOREIGN KEY (id_medico) REFERENCES pessoa(id)
);

-- nova tabela oonsulta
CREATE TABLE consulta (
                          id SERIAL PRIMARY KEY,
                          disponibilidade_id INTEGER NOT NULL UNIQUE,
                          id_paciente INTEGER NOT NULL,
                          id_medico INTEGER NOT NULL,
                          valor NUMERIC(10, 2),
                          observacao TEXT,
                          status VARCHAR(20) NOT NULL DEFAULT 'AGENDADA' CHECK (status IN ('AGENDADA', 'REALIZADA', 'CANCELADA', 'FALTA')),
                          FOREIGN KEY (disponibilidade_id) REFERENCES disponibilidade(id) ON DELETE RESTRICT,
                          FOREIGN KEY (id_paciente) REFERENCES pessoa(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_medico) REFERENCES pessoa(id) ON DELETE CASCADE
);


-- Criando a tabela consulta
CREATE TABLE consulta (
                          id_consulta SERIAL PRIMARY KEY,
                          id_disponibilidade INTEGER UNIQUE,  -- Relação 1:1 com o agendamento
                          data DATE NOT NULL,
                          hora TIME NOT NULL,             -- Adicionado para registrar o horário específico
                          valor NUMERIC(10, 2) NOT NULL,
                          observacao TEXT,
                          id_paciente INTEGER NOT NULL,
                          id_medico INTEGER NOT NULL,
                          status VARCHAR(20) NOT NULL DEFAULT 'AGENDADA' CHECK (status IN ('AGENDADA', 'REALIZADA', 'CANCELADA', 'FALTA')),
                          FOREIGN KEY (id_paciente) REFERENCES pessoa(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_medico) REFERENCES pessoa(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_disponibilidade) REFERENCES agendamento(id_agendamento) ON DELETE CASCADE
);

-- Inserindo médicos na tabela pessoa
INSERT INTO pessoa (nome, tipo, crm) VALUES
                                         ('Dr. Carlos Silva', 'MEDICO', 'CRM-TO-12345'),
                                         ('Dra. Mariana Costa', 'MEDICO', 'CRM-TO-67890'),
                                         ('Dr. Pedro Almeida', 'MEDICO', 'CRM-TO-54321');

-- Inserindo pacientes na tabela pessoa
INSERT INTO pessoa (nome, tipo, telefone) VALUES
                                              ('Ana Souza', 'PACIENTE', '(63) 91234-5678'),
                                              ('José Santos', 'PACIENTE', '(63) 99876-5432'),
                                              ('Maria Oliveira', 'PACIENTE', '(63) 98765-4321');

-- Inserindo consultas
INSERT INTO consulta (data, valor, observacao, id_paciente, id_medico) VALUES
                                                                           ('2025-05-10', 250.00, 'Consulta de rotina', 4, 1),
                                                                           ('2025-05-11', 300.00, 'Retorno para avaliação', 5, 2),
                                                                           ('2025-05-12', 150.00, 'Consulta de emergência', 6, 3),
                                                                           ('2025-05-13', 200.00, 'Check-up anual', 4, 3);

------------- a inserir --------------



INSERT INTO disponibilidade (id, id_medico,data, hora_inicio,hora_fim) VALUES
                                                                           (101, 1, '09:00:00', '09:30:00'),
                                                                           (102, 1, '09:30:00', '10:00:00'),
                                                                           (103, 1, '10:00:00', '10:30:00'),
                                                                           (104, 1, '10:30:00', '11:00:00');

INSERT INTO disponibilidade (id, id_medico, data, hora_inicio, hora_fim, status) VALUES
                                                                                     (101, 1, '2025-09-02', '09:00:00', '09:30:00', 'AGENDADO'),
                                                                                     (103, 1, '2025-09-02', '10:00:00', '10:30:00', 'AGENDADO');

INSERT INTO disponibilidade (id, id_medico, data, hora_inicio, hora_fim, status) VALUES
    (203, 2, '2025-09-04', '15:30:00', '16:15:00', 'AGENDADO');

INSERT INTO disponibilidade (id, id_medico, data, hora_inicio, hora_fim, status) VALUES
    (204, 2, '2025-09-04', '15:30:00', '16:15:01', 'DISPONIVEL');
