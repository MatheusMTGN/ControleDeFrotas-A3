package br.com.a3_frotas.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaminhaoTest {
    @Test
    void testDefaultConstructor() {
        Caminhao caminhao = new Caminhao();

        assertNull(caminhao.getId());
        assertNull(caminhao.getPlaca());
        assertNull(caminhao.getModel());
        assertEquals(0, caminhao.getAno());
    }

    @Test
    void testParameterizedConstructor() {

        Caminhao caminhao = new Caminhao("ABC123", 2020, "Modelo X");

        assertEquals("ABC123", caminhao.getPlaca());
        assertEquals(2020, caminhao.getAno());
        assertEquals("Modelo X", caminhao.getModel());
    }

    @Test
    void testSetAndGetPlaca() {
        Caminhao caminhao = new Caminhao();


        caminhao.setPlaca("DEF456");
        assertEquals("DEF456", caminhao.getPlaca());


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            caminhao.setPlaca("123");
        });
        assertEquals("A placa deve conter 6 dígitos", exception.getMessage());
    }

    @Test
    void testSetAndGetModel() {
        Caminhao caminhao = new Caminhao();


        caminhao.setModel("Modelo Y");
        assertEquals("Modelo Y", caminhao.getModel());
    }

    @Test
    void testSetAndGetAno() {
        Caminhao caminhao = new Caminhao();


        caminhao.setAno(2022);
        assertEquals(2022, caminhao.getAno());
    }

    @Test
    void testSetAndGetId() {
        Caminhao caminhao = new Caminhao();


        caminhao.setId(100L);
        assertEquals(100L, caminhao.getId());
    }

    @Test
    void testPlacaEdgeCases() {
        Caminhao caminhao = new Caminhao();


        Exception exceptionTooShort = assertThrows(IllegalArgumentException.class, () -> {
            caminhao.setPlaca("12345");
        });
        assertEquals("A placa deve conter 6 dígitos", exceptionTooShort.getMessage());

        Exception exceptionTooLong = assertThrows(IllegalArgumentException.class, () -> {
            caminhao.setPlaca("1234567");
        });
        assertEquals("A placa deve conter 6 dígitos", exceptionTooLong.getMessage());
    }
}
