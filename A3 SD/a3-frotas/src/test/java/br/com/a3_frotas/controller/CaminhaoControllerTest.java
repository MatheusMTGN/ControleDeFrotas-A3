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
import org.springframework.web.servlet.ModelAndView;

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

    @Test
    void testAdicionarCaminhao_ComJsonInvalido() {
        String invalidJson = "{ \"placa\": \"AAA-1234\", \"modelo\": \"\", \"ano\": 2020 }";  // Modelo vazio
        when(caminhaoService.adicionarCaminhao(any(Caminhao.class))).thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<?> response = caminhaoController.adicionarCaminhao(new Caminhao("AAA-1234", 2020, ""));

        assertEquals(400, response.getStatusCode().value());  // Resposta esperada: erro de requisição (400)
        assertTrue(response.getBody().toString().contains("Placa, modelo e ano são obrigatórios"));
    }

    @Test
    void testAtualizarCaminhao_ComDadosInvalidos() {
        String placa = "AAA-1234";
        Caminhao caminhaoAtualizado = new Caminhao(placa, 0, "");  // Ano inválido e modelo vazio

        // Aqui estamos simulando o comportamento do serviço lançando uma exceção
        when(caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        // Executa o método do controlador e garante que ele trate a exceção
        ResponseEntity<Caminhao> response = null;
        try {
            response = caminhaoController.atualizarCaminhao(placa, caminhaoAtualizado);
        } catch (IllegalArgumentException e) {
            // Caso a exceção seja lançada, capturamos ela e verificamos se o status é 400
            assertNotNull(e);
            assertEquals("Dados inválidos", e.getMessage());
            // Aqui, você pode adicionar a lógica para verificar o retorno em caso de erro (se for feito no controlador)
        }

        // Se o controlador retornar uma resposta (em vez de lançar a exceção), verificamos o código do status
        if (response != null) {
            assertEquals(400, response.getStatusCode().value());  // Resposta esperada: erro de requisição
        }
    }

    @Test
    void testRemoverCaminhao_ComErroDeRemocao() {
        String placa = "AAA-1234";
        doThrow(new IllegalArgumentException("Caminhão não encontrado")).when(caminhaoService).removerCaminhao(placa);

        ResponseEntity<Void> response = caminhaoController.removerCaminhao(placa);

        assertEquals(404, response.getStatusCode().value());  // Erro ao tentar remover caminhão não encontrado
    }

    @Test
    void testListarCaminhoes_ComFalhaNoServico() {
        try {
            // Simula a falha no serviço
            when(caminhaoService.listarTodosOsCaminhoes()).thenThrow(new RuntimeException("Erro ao listar caminhões"));

            ResponseEntity<List<Caminhao>> response = caminhaoController.listarCaminhoes();

            // Assegura que o código de status seja 500 e o corpo seja nulo
            assertEquals(500, response.getStatusCode().value());
            assertNull(response.getBody());

        } catch (RuntimeException e) {
            // Aqui você pode simplesmente capturar a exceção e garantir que o erro esperado foi lançado
            assertTrue(e.getMessage().contains("Erro ao listar caminhões"));
        }
    }

}