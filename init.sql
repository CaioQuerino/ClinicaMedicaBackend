-- DROP E CREATE DATABASE COM UTF-8
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
DROP DATABASE IF EXISTS clinica_db;
CREATE DATABASE clinica_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica_db;

-- =====================================================
-- TABELA DE PROFISSIONAIS (BASE)
-- =====================================================
CREATE TABLE profissional (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    conselho VARCHAR(20) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABELAS DE PROFISSIONAIS ESPECIALIZADOS
-- =====================================================
CREATE TABLE medicos (
    id BIGINT PRIMARY KEY,
    numero_registro VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id) REFERENCES profissional(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE dentistas (
    id BIGINT PRIMARY KEY,
    numero_registro VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id) REFERENCES profissional(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE psicologos (
    id BIGINT PRIMARY KEY,
    numero_registro VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id) REFERENCES profissional(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABELA DE PLANOS DE SAÚDE
-- =====================================================
CREATE TABLE planos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    operadora VARCHAR(50),
    tipo VARCHAR(50),
    desconto DOUBLE NOT NULL DEFAULT 0.0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    observacoes VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABELA DE PACIENTES
-- =====================================================
CREATE TABLE pacientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(200),
    plano_id BIGINT,
    FOREIGN KEY (plano_id) REFERENCES planos(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABELA DE CONSULTAS
-- =====================================================
CREATE TABLE consultas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profissional_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data_hora DATETIME NOT NULL,
    observacao VARCHAR(500),
    status VARCHAR(50),
    FOREIGN KEY (profissional_id) REFERENCES profissional(id) ON DELETE CASCADE,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTS DE DADOS DE EXEMPLO (com acentos corrigidos)
-- =====================================================

-- Inserir Profissionais
INSERT INTO profissional (id, nome, conselho, especialidade, telefone, email) VALUES
(1, 'Dr. João Silva', 'CRM', 'Cardiologia', '(11) 99999-9999', 'joao.silva@clinica.com'),
(2, 'Dra. Maria Santos', 'CRM', 'Pediatria', '(11) 98888-8888', 'maria.santos@clinica.com'),
(3, 'Dr. Pedro Costa', 'CRO', 'Ortodontia', '(11) 97777-7777', 'pedro.costa@clinica.com'),
(4, 'Dra. Ana Paula', 'CRP', 'Psicologia Clínica', '(11) 96666-6666', 'ana.paula@clinica.com'),
(5, 'Dr. Roberto Alves', 'CRM', 'Ortopedia', '(11) 95555-5555', 'roberto.alves@clinica.com'),
(6, 'Dra. Carla Mendes', 'CRO', 'Implantodontia', '(11) 94444-4444', 'carla.mendes@clinica.com');

-- Inserir Médicos
INSERT INTO medicos (id, numero_registro) VALUES 
(1, 'CRM12345'), 
(2, 'CRM67890'),
(5, 'CRM54321');

-- Inserir Dentistas
INSERT INTO dentistas (id, numero_registro) VALUES 
(3, 'CRO54321'),
(6, 'CRO98765');

-- Inserir Psicólogos
INSERT INTO psicologos (id, numero_registro) VALUES 
(4, 'CRP98765');

-- Inserir Planos de Saúde (com acentos corrigidos)
INSERT INTO planos (codigo, nome, operadora, tipo, desconto, ativo, observacoes) VALUES
('UNIMED001', 'Unimed Nacional', 'Unimed', 'Empresarial', 20.0, true, 'Cobertura nacional completa'),
('BRADESCO01', 'Bradesco Saúde', 'Bradesco', 'Premium', 25.0, true, 'Rede referenciada nacional'),
('AMIL100', 'Amil 100', 'Amil', 'Básico', 15.0, true, 'Cobertura regional'),
('SULAMERICA', 'SulAmérica', 'SulAmérica', 'Executivo', 30.0, true, 'Atendimento diferenciado'),
('PORTO_SEGURO', 'Porto Seguro Saúde', 'Porto Seguro', 'Standard', 18.0, true, 'Rede própria'),
('NOTRE_DAME', 'NotreDame Intermédica', 'NotreDame', 'Essencial', 22.0, true, 'Cobertura básica'),
('HAPVIDA', 'Hapvida Saúde', 'Hapvida', 'Completo', 28.0, true, 'Atendimento 24 horas'),
('GNDI', 'GNDI Saúde', 'GNDI', 'Premium', 35.0, true, 'Cobertura internacional'),
('CASSI', 'CASSI', 'CASSI', 'Corporativo', 40.0, true, 'Para bancários'),
('GEAP', 'GEAP Saúde', 'GEAP', 'Servidor', 32.0, true, 'Para servidores públicos');

-- Inserir Pacientes
INSERT INTO pacientes (id, nome, cpf, data_nascimento, telefone, endereco, plano_id) VALUES
(1, 'Ana Oliveira', '12345678901', '1990-05-15', '(11) 97777-7777', 'Rua A, 123 - São Paulo, SP', 1),
(2, 'Carlos Souza', '98765432109', '1985-08-20', '(11) 96666-6666', 'Rua B, 456 - São Paulo, SP', 2),
(3, 'Mariana Lima', '45678912345', '1995-12-10', '(11) 95555-5555', 'Rua C, 789 - São Paulo, SP', 3),
(4, 'José Ferreira', '78912345678', '1980-03-25', '(11) 94444-4444', 'Rua D, 101 - São Paulo, SP', 4),
(5, 'Patrícia Gomes', '32165498701', '1992-07-08', '(11) 93333-3333', 'Rua E, 202 - São Paulo, SP', 5),
(6, 'Ricardo Martins', '65498732109', '1988-11-30', '(11) 92222-2222', 'Rua F, 303 - São Paulo, SP', NULL);

-- Inserir Consultas
INSERT INTO consultas (id, profissional_id, paciente_id, data_hora, observacao, status) VALUES
(1, 1, 1, '2026-04-20 14:30:00', 'Primeira consulta - Checkup anual', 'AGENDADA'),
(2, 2, 2, '2026-04-21 09:00:00', 'Retorno para exames', 'AGENDADA'),
(3, 3, 3, '2026-04-22 15:00:00', 'Avaliação ortodôntica', 'AGENDADA'),
(4, 4, 4, '2026-04-23 11:00:00', 'Terapia cognitivo-comportamental', 'REALIZADA'),
(5, 5, 5, '2026-04-24 16:30:00', 'Dor no joelho direito', 'CANCELADA'),
(6, 1, 6, '2026-04-25 10:00:00', 'Checkup cardiológico', 'AGENDADA');

-- Resetar AUTO_INCREMENT
ALTER TABLE profissional AUTO_INCREMENT = 7;
ALTER TABLE planos AUTO_INCREMENT = 11;
ALTER TABLE pacientes AUTO_INCREMENT = 7;
ALTER TABLE consultas AUTO_INCREMENT = 7;

-- =====================================================
-- CONSULTAS DE VERIFICAÇÃO
-- =====================================================
SELECT '=== PROFISSIONAIS ===' as '';
SELECT p.id, p.nome, p.conselho, p.especialidade, 
       COALESCE(m.numero_registro, d.numero_registro, ps.numero_registro) as numero_registro
FROM profissional p
LEFT JOIN medicos m ON p.id = m.id
LEFT JOIN dentistas d ON p.id = d.id
LEFT JOIN psicologos ps ON p.id = ps.id;

SELECT '=== PLANOS DE SAÚDE ===' as '';
SELECT id, codigo, nome, operadora, tipo, CONCAT(desconto, '%') as desconto, 
       CASE WHEN ativo THEN '✅ Ativo' ELSE '❌ Inativo' END as status
FROM planos WHERE ativo = true;

SELECT '=== PACIENTES ===' as '';
SELECT p.id, p.nome, p.cpf, p.telefone, pl.nome as plano_nome, CONCAT(pl.desconto, '%') as desconto
FROM pacientes p
LEFT JOIN planos pl ON p.plano_id = pl.id;

SELECT '=== CONSULTAS ===' as '';
SELECT c.id, pr.nome as profissional, pa.nome as paciente, c.data_hora, c.status
FROM consultas c
JOIN profissional pr ON c.profissional_id = pr.id
JOIN pacientes pa ON c.paciente_id = pa.id
ORDER BY c.data_hora;