-- Criando o banco de dados
CREATE DATABASE clinica;

-- Conectando ao banco de dados
\c clinica;

-- Criando a tabela medico
CREATE TABLE medico (
    id_med SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL
);

-- Criando a tabela paciente
CREATE TABLE paciente (
    id_paciente SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15)
);

-- Criando a tabela consulta
CREATE TABLE consulta (
        id_consulta SERIAL PRIMARY KEY,
        data DATE NOT NULL,
        valor NUMERIC(10, 2) NOT NULL,
        observacao TEXT,
        id_paciente INTEGER NOT NULL,
        id_medico INTEGER NOT NULL,
        FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente) ON DELETE CASCADE,
        FOREIGN KEY (id_medico) REFERENCES medico(id_med) ON DELETE CASCADE
);

-- Médicos
INSERT INTO medico (nome, crm) VALUES 
('Dr. Carlos Silva', 'CRM-TO-12345'),
('Dra. Mariana Costa', 'CRM-TO-67890'),
('Dr. Pedro Almeida', 'CRM-TO-54321');


-- Pacientes
INSERT INTO paciente (nome, telefone) VALUES 
('Ana Souza', '(63) 91234-5678'),
('José Santos', '(63) 99876-5432'),
('Maria Oliveira', '(63) 98765-4321');


-- Consultas
INSERT INTO consulta (data, valor, observacao, id_paciente, id_medico) VALUES 
('2025-05-10', 250.00, 'Consulta de rotina', 1, 1),
('2025-05-11', 300.00, 'Retorno para avaliação', 2, 2),
('2025-05-12', 150.00, 'Consulta de emergência', 3, 3),
('2025-05-13', 200.00, 'Check-up anual', 1, 3);

