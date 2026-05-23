-- Script SQL - Sistema de Estacionamento
-- Banco de dados: SQLite

PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS veiculos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    placa TEXT NOT NULL UNIQUE,
    modelo TEXT NOT NULL,
    cor TEXT NOT NULL,
    tipo TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS vagas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    numero INTEGER NOT NULL UNIQUE,
    ocupada INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS movimentacoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    veiculo_id INTEGER NOT NULL,
    vaga_id INTEGER NOT NULL,
    data_entrada TEXT NOT NULL,
    data_saida TEXT,
    valor_pago REAL,

    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id)
);

INSERT OR IGNORE INTO vagas (numero, ocupada)
VALUES
(1, 0),
(2, 0),
(3, 0),
(4, 0),
(5, 0);
