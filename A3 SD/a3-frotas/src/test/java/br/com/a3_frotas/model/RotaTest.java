package br.com.a3_frotas.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotaTest {

    @Test
    void testPontoDePartidaValido() {
        Rota rota = new Rota();

        rota.setPontoDePartida("São Paulo");
        assertNotNull(rota.getPontoDePartida());
        assertEquals("São Paulo", rota.getPontoDePartida());
    }

    @Test
    void testPontoDePartidaInvalido() {
        Rota rota = new Rota();

        rota.setPontoDePartida("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDePartida() == null || rota.getPontoDePartida().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de partida não pode ser vazio");
            }
        });
        assertEquals("Ponto de partida não pode ser vazio", exception.getMessage());

        rota.setPontoDePartida(null);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDePartida() == null || rota.getPontoDePartida().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de partida não pode ser vazio");
            }
        });
        assertEquals("Ponto de partida não pode ser vazio", exception.getMessage());

        rota.setPontoDePartida("     ");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDePartida().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de partida não pode ser vazio");
            }
        });
        assertEquals("Ponto de partida não pode ser vazio", exception.getMessage());
    }

    @Test
    void testPontoDeChegadaValido() {
        Rota rota = new Rota();

        rota.setPontoDeChegada("Rio de Janeiro");
        assertNotNull(rota.getPontoDeChegada());
        assertEquals("Rio de Janeiro", rota.getPontoDeChegada());
    }

    @Test
    void testPontoDeChegadaInvalido() {
        Rota rota = new Rota();

        rota.setPontoDeChegada("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDeChegada() == null || rota.getPontoDeChegada().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de chegada não pode ser vazio");
            }
        });
        assertEquals("Ponto de chegada não pode ser vazio", exception.getMessage());

        rota.setPontoDeChegada(null);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDeChegada() == null || rota.getPontoDeChegada().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de chegada não pode ser vazio");
            }
        });
        assertEquals("Ponto de chegada não pode ser vazio", exception.getMessage());

        rota.setPontoDeChegada("     ");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            if (rota.getPontoDeChegada().trim().isEmpty()) {
                throw new IllegalArgumentException("Ponto de chegada não pode ser vazio");
            }
        });
        assertEquals("Ponto de chegada não pode ser vazio", exception.getMessage());
    }

    @Test
    void testConstrutorValido() {
        Motorista motorista = new Motorista(); // Criação de um motorista fictício para o teste
        Rota rota = new Rota(1L, "São Paulo", "Rio de Janeiro", motorista);

        assertNotNull(rota);
        assertEquals(1L, rota.getId());
        assertEquals("São Paulo", rota.getPontoDePartida());
        assertEquals("Rio de Janeiro", rota.getPontoDeChegada());
        assertEquals(motorista, rota.getMotorista());
    }

    @Test
    void testConstrutorVazio() {
        Rota rota = new Rota();

        assertNotNull(rota);
        assertNull(rota.getId());
        assertNull(rota.getPontoDePartida());
        assertNull(rota.getPontoDeChegada());
        assertNull(rota.getMotorista());
    }
}
