-- Criação da tabela Caminhão
CREATE TABLE caminhoes (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           placa VARCHAR(10) NOT NULL UNIQUE,
                           modelo VARCHAR(50) NOT NULL,
                           ano INT NOT NULL
);

-- Criação da tabela Motoristas
CREATE TABLE motoristas (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            cpf VARCHAR(14) NOT NULL UNIQUE,
                            cnh VARCHAR(20) NOT NULL UNIQUE,
                            data_nascimento DATE NOT NULL,
                            telefone VARCHAR(15) NOT NULL,
                            ativo BOOLEAN NOT NULL DEFAULT TRUE,
                            email VARCHAR(255),
                            caminhao_id BIGINT NOT NULL UNIQUE,
                            CONSTRAINT fk_motorista_caminhao FOREIGN KEY (caminhao_id) REFERENCES caminhoes(id)
);

-- Criação da tabela Rota
CREATE TABLE rotas (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       ponto_de_partida VARCHAR(255) NOT NULL,
                       ponto_de_chegada VARCHAR(255) NOT NULL,
                       motorista_id BIGINT,
                       caminhao_id BIGINT,
                       CONSTRAINT fk_rota_motorista FOREIGN KEY (motorista_id) REFERENCES motoristas(id) ON DELETE SET NULL,
                       CONSTRAINT fk_rota_caminhao FOREIGN KEY (caminhao_id) REFERENCES caminhoes(id) ON DELETE CASCADE
);
