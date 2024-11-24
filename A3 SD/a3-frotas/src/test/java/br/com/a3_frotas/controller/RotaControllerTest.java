package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.MotoristaService;
import br.com.a3_frotas.service.RotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class RotaControllerTest {
    @Mock
    private RotaService rotaService;

    @Mock
    private MotoristaService motoristaService;

    @InjectMocks
    private RotaController rotaController;

    private Rota rota;
    private Motorista motorista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um motorista e uma rota para os testes
        motorista = new Motorista();
        motorista.setId(1L);
        motorista.setNome("João");

        rota = new Rota();
        rota.setId(1L);
        rota.setMotorista(motorista);
        rota.setPontoDePartida("Local A");
        rota.setPontoDeChegada("Local B");
    }

    @Test
    void testCadastrarRota_Success() {
        when(motoristaService.filtrarPorId(1L)).thenReturn(motorista);
        when(rotaService.cadastrarRota(rota)).thenReturn(rota);

        ResponseEntity<Rota> response = rotaController.cadastrarRota(rota);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(rota, response.getBody());
    }

    @Test
    void testCadastrarRota_BadRequest_NullMotorista() {
        Rota rotaInvalida = new Rota();
        ResponseEntity<Rota> response = rotaController.cadastrarRota(rotaInvalida);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCadastrarRota_BadRequest_EmptyPoints() {
        Rota rotaInvalida = new Rota();
        rotaInvalida.setMotorista(motorista);
        ResponseEntity<Rota> response = rotaController.cadastrarRota(rotaInvalida);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCadastrarRota_NotFound_Motorista() {
        when(motoristaService.filtrarPorId(1L)).thenReturn(null);

        ResponseEntity<Rota> response = rotaController.cadastrarRota(rota);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testListarRotas() {
        List<Rota> listaRotas = new ArrayList<>();
        listaRotas.add(rota);
        when(rotaService.listarRotas()).thenReturn(listaRotas);

        ResponseEntity<List<Rota>> response = rotaController.listarRotas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaRotas, response.getBody());
    }

    @Test
    void testListarRotas_NotFound() {
        when(rotaService.listarRotas()).thenReturn(new ArrayList<>());  // Retorna lista vazia

        ResponseEntity<List<Rota>> response = rotaController.listarRotas();

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Espera um status 200 OK
        assertTrue(response.getBody().isEmpty()); // Confirma que o corpo da resposta é uma lista vazia
    }

    @Test
    void testAtualizarRota_Success() {
        when(rotaService.atualizarRota(1L, rota)).thenReturn(rota);

        ResponseEntity<?> response = rotaController.atualizarRota(1L, rota);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rota, response.getBody());
    }

    @Test
    void testAtualizarRota_NotFound() {
        when(rotaService.atualizarRota(1L, rota)).thenThrow(new IllegalArgumentException("Rota não encontrada"));

        ResponseEntity<?> response = rotaController.atualizarRota(1L, rota);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Rota não encontrada", response.getBody());
    }

    @Test
    void testRemoverRota_Success() {
        doNothing().when(rotaService).excluirRota(1L);

        ResponseEntity<Void> response = rotaController.removerRota(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rotaService, times(1)).excluirRota(1L);
    }

    @Test
    void testFiltrarPorMotorista_Success() {
        List<Rota> listaRotas = new ArrayList<>();
        listaRotas.add(rota);
        when(rotaService.filtrarPorMotorista(1L)).thenReturn(listaRotas);

        ResponseEntity<List<Rota>> response = rotaController.filtrarPorMotorista(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaRotas, response.getBody());
    }

    @Test
    void testFiltrarPorMotorista_NotFound() {
        when(rotaService.filtrarPorMotorista(1L)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Rota>> response = rotaController.filtrarPorMotorista(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
