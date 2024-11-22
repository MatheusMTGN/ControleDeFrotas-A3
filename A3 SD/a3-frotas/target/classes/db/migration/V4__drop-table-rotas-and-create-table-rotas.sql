DROP TABLE IF EXISTS rotas;


CREATE TABLE rotas (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       ponto_de_partida VARCHAR(255) NOT NULL,
                       ponto_de_chegada VARCHAR(255) NOT NULL,
                       motorista_id BIGINT, -- Relacionamento com a tabela Motoristas
                       CONSTRAINT fk_rota_motorista FOREIGN KEY (motorista_id) REFERENCES motoristas(id) ON DELETE SET NULL
);
