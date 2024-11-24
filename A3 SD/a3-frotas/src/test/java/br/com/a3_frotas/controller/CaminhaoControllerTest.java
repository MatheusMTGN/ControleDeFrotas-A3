package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.service.CaminhaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CaminhaoControllerTest {

    @Mock
    private CaminhaoService caminhaoService;

    @InjectMocks
    private CaminhaoController caminhaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionarCaminhao_ComSucesso() {
        Caminhao novoCaminhao = new Caminhao("AAA-1234", 2020, "Modelo X");
        when(caminhaoService.adicionarCaminhao(any(Caminhao.class))).thenReturn(novoCaminhao);

        ResponseEntity<?> response = caminhaoController.adicionarCaminhao(novoCaminhao);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(caminhaoService, times(1)).adicionarCaminhao(novoCaminhao);
    }

    @Test
    void testAdicionarCaminhao_ComDadosInvalidos() {
        Caminhao novoCaminhao = new Caminhao(null, 0, "");

        ResponseEntity<?> response = caminhaoController.adicionarCaminhao(novoCaminhao);

        assertEquals(400, response.getStatusCode().value());
        verify(caminhaoService, never()).adicionarCaminhao(any(Caminhao.class));
    }

    @Test
    void testBuscarCaminhao_ComSucesso() {
        String placa = "AAA-1234";
        Caminhao caminhao = new Caminhao(placa, 2020, "Modelo X");
        when(caminhaoService.filtrarPorPlaca(placa)).thenReturn(caminhao);

        ResponseEntity<Caminhao> response = caminhaoController.buscarCaminhao(placa);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(placa, response.getBody().getPlaca());
        verify(caminhaoService, times(1)).filtrarPorPlaca(placa);
    }

    @Test
    void testBuscarCaminhao_ComPlacaNaoEncontrada() {
        String placa = "AAA-1234";
        when(caminhaoService.filtrarPorPlaca(placa)).thenReturn(null);

        ResponseEntity<Caminhao> response = caminhaoController.buscarCaminhao(placa);

        assertEquals(404, response.getStatusCode().value());
        verify(caminhaoService, times(1)).filtrarPorPlaca(placa);
    }

    @Test
    void testAtualizarCaminhao_ComSucesso() {
        String placa = "AAA-1234";
        Caminhao caminhaoAtualizado = new Caminhao(placa, 2021, "Modelo Y");
        when(caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado)).thenReturn(caminhaoAtualizado);

        ResponseEntity<Caminhao> response = caminhaoController.atualizarCaminhao(placa, caminhaoAtualizado);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(placa, response.getBody().getPlaca());
        verify(caminhaoService, times(1)).atualizarCaminhao(placa, caminhaoAtualizado);
    }

    @Test
    void testAtualizarCaminhao_ComPlacaNaoEncontrada() {
        String placa = "AAA-1234";
        Caminhao caminhaoAtualizado = new Caminhao(placa, 2021, "Modelo Y");
        when(caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado)).thenReturn(null);

        ResponseEntity<Caminhao> response = caminhaoController.atualizarCaminhao(placa, caminhaoAtualizado);

        assertEquals(404, response.getStatusCode().value());
        verify(caminhaoService, times(1)).atualizarCaminhao(placa, caminhaoAtualizado);
    }

    @Test
    void testRemoverCaminhao_ComSucesso() {
        String placa = "AAA-1234";
        doNothing().when(caminhaoService).removerCaminhao(placa);

        ResponseEntity<Void> response = caminhaoController.removerCaminhao(placa);

        assertEquals(204, response.getStatusCode().value());
        verify(caminhaoService, times(1)).removerCaminhao(placa);
    }

    @Test
    void testListarCaminhoes() {
        List<Caminhao> listaCaminhoes = List.of(new Caminhao("AAA-1234", 2020, "Modelo X"));
        when(caminhaoService.listarTodosOsCaminhoes()).thenReturn(listaCaminhoes);

        ResponseEntity<List<Caminhao>> response = caminhaoController.listarCaminhoes();

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(caminhaoService, times(1)).listarTodosOsCaminhoes();
    }
}
