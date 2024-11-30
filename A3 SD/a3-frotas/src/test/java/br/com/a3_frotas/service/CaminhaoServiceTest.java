package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.repository.CaminhaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CaminhaoServiceTest {

    @MockBean
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private CaminhaoService caminhaoService;

    private Caminhao caminhao;

    @BeforeEach
    void setUp() {
        caminhao = new Caminhao("ABC-1234", 2020, "Modelo A"); // Corrigindo a ordem dos parâmetros
    }

    @Test
    void testAdicionarCaminhao_Success() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.empty());
        when(caminhaoRepository.save(caminhao)).thenReturn(caminhao);

        Caminhao result = caminhaoService.adicionarCaminhao(caminhao);

        assertNotNull(result);
        assertEquals(caminhao.getPlaca(), result.getPlaca());
    }

    @Test
    void testAdicionarCaminhao_Excecao_PlacaExistente() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.of(caminhao));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            caminhaoService.adicionarCaminhao(caminhao);
        });

        assertEquals("Já existe um veículo adicionado com esta placa", thrown.getMessage());
    }

    @Test
    void testFiltrarPorPlaca_Success() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.of(caminhao));

        Caminhao result = caminhaoService.filtrarPorPlaca(caminhao.getPlaca());

        assertNotNull(result);
        assertEquals(caminhao.getPlaca(), result.getPlaca());
    }

    @Test
    void testFiltrarPorPlaca_NotFound() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.empty());

        Caminhao result = caminhaoService.filtrarPorPlaca(caminhao.getPlaca());

        assertNull(result);
    }

    @Test
    void testAtualizarCaminhao_Success() {
        // Corrigindo a ordem dos parâmetros (placa, ano, modelo)
        Caminhao caminhaoAtualizado = new Caminhao("ABC-1234", 2022, "Modelo B");
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.of(caminhao));
        when(caminhaoRepository.save(caminhao)).thenReturn(caminhaoAtualizado);

        Caminhao result = caminhaoService.atualizarCaminhao(caminhao.getPlaca(), caminhaoAtualizado);

        assertNotNull(result);
        assertEquals("Modelo B", result.getModel()); // Alterado para getModel()
        assertEquals(2022, result.getAno());
    }

    @Test
    void testAtualizarCaminhao_NotFound() {
        // Corrigindo a ordem dos parâmetros (placa, ano, modelo)
        Caminhao caminhaoAtualizado = new Caminhao("ABC-1234", 2022, "Modelo B");
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            caminhaoService.atualizarCaminhao(caminhao.getPlaca(), caminhaoAtualizado);
        });

        assertEquals("Não há nenhum caminhão cadastrado com esta placa.", thrown.getMessage());
    }

    @Test
    void testRemoverCaminhao_Success() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.of(caminhao));
        doNothing().when(caminhaoRepository).delete(caminhao);

        assertDoesNotThrow(() -> caminhaoService.removerCaminhao(caminhao.getPlaca()));
    }

    @Test
    void testRemoverCaminhao_NotFound() {
        when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            caminhaoService.removerCaminhao(caminhao.getPlaca());
        });

        assertEquals("Não há nenhum caminhão cadastrado com esta placa.", thrown.getMessage());
    }

    @Test
    void testListarTodosOsCaminhoes() {
        when(caminhaoRepository.findAll()).thenReturn(List.of(caminhao));

        var result = caminhaoService.listarTodosOsCaminhoes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(caminhao.getPlaca(), result.get(0).getPlaca());
    }
}
