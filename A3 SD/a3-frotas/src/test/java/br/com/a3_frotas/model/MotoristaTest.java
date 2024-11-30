package br.com.a3_frotas.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class MotoristaTest {
    @Test
    void testDefaultConstructor() {
        Motorista motorista = new Motorista();

        assertNull(motorista.getId());
        assertNull(motorista.getNome());
        assertNull(motorista.getCpf());
        assertNull(motorista.getDataNascimento());
        assertNull(motorista.getCnh());
        assertNull(motorista.getTelefone());
        assertTrue(motorista.getAtivo());
        assertNull(motorista.getEmail());
        assertNull(motorista.getRotas());
        assertNull(motorista.getCaminhao());
    }

    @Test
    void testParameterizedConstructor() {
        Caminhao caminhao = new Caminhao("DEF123", 2020, "Modelo Y");
        Motorista motorista = new Motorista(
                caminhao,
                true,
                "11999999999",
                "12345678910",
                LocalDate.of(1990, 1, 1),
                "12345678901",
                "João Silva",
                "joao@gmail.com"
        );

        assertEquals(caminhao, motorista.getCaminhao());
        assertTrue(motorista.getAtivo());
        assertEquals("11999999999", motorista.getTelefone());
        assertEquals("12345678910", motorista.getCnh());
        assertEquals(LocalDate.of(1990, 1, 1), motorista.getDataNascimento());
        assertEquals("12345678901", motorista.getCpf());
        assertEquals("João Silva", motorista.getNome());
        assertEquals("joao@gmail.com", motorista.getEmail());
    }

    @Test
    void testSetDataNascimentoValid() {
        Motorista motorista = new Motorista();

        LocalDate dataValida = LocalDate.of(1990, 1, 1);
        motorista.setDataNascimento(dataValida);

        assertEquals(dataValida, motorista.getDataNascimento());
    }

    @Test
    void testSetDataNascimentoInvalidNull() {
        Motorista motorista = new Motorista();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            motorista.setDataNascimento(null);
        });
        assertEquals("A data de nascimento não pode ser nula.", exception.getMessage());
    }

    @Test
    void testSetDataNascimentoInvalidUnderage() {
        Motorista motorista = new Motorista();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            motorista.setDataNascimento(LocalDate.now().minusYears(17));
        });
        assertEquals("O motorista deve ser maior de 18 anos.", exception.getMessage());
    }

    @Test
    void testSetCpfValid() {
        Motorista motorista = new Motorista();

        motorista.setCpf("12345678901");
        assertEquals("12345678901", motorista.getCpf());
    }

    @Test
    void testSetCpfInvalid() {
        Motorista motorista = new Motorista();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            motorista.setCpf("123");
        });
        assertEquals("O CPF deve contar 11 dígitos númericos", exception.getMessage());
    }

    @Test
    void testSetCnhValid() {
        Motorista motorista = new Motorista();

        motorista.setCnh("12345678901");
        assertEquals("12345678901", motorista.getCnh());
    }

    @Test
    void testSetCnhInvalid() {
        Motorista motorista = new Motorista();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            motorista.setCnh("123");
        });
        assertEquals("A CNH deve conter 11 dígitos númericos", exception.getMessage());
    }

    @Test
    void testSetAndGetTelefone() {
        Motorista motorista = new Motorista();

        motorista.setTelefone("11999999999");
        assertEquals("11999999999", motorista.getTelefone());
    }

    @Test
    void testSetAndGetEmail() {
        Motorista motorista = new Motorista();

        motorista.setEmail("joao@gmail.com");
        assertEquals("joao@gmail.com", motorista.getEmail());
    }

    @Test
    void testSetAndGetAtivo() {
        Motorista motorista = new Motorista();

        motorista.setAtivo(false);
        assertFalse(motorista.getAtivo());

        motorista.setAtivo(true);
        assertTrue(motorista.getAtivo());
    }

    @Test
    void testSetAndGetNome() {
        Motorista motorista = new Motorista();

        motorista.setNome("João Silva");
        assertEquals("João Silva", motorista.getNome());
    }

    @Test
    void testSetAndGetCaminhao() {
        Motorista motorista = new Motorista();
        Caminhao caminhao = new Caminhao("DEF123", 2020, "Modelo Y");

        motorista.setCaminhao(caminhao);
        assertEquals(caminhao, motorista.getCaminhao());
    }
}
